package me.vitornascimento.trendingrepositories.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.vitornascimento.trendingrepositories.data.local.dao.TrendingRepositoriesDao
import me.vitornascimento.trendingrepositories.data.local.entity.TrendingRepositoryEntity
import me.vitornascimento.trendingrepositories.data.mapper.toDomainModel
import me.vitornascimento.trendingrepositories.data.mapper.toEntityModelList
import me.vitornascimento.trendingrepositories.data.remote.GithubService
import me.vitornascimento.trendingrepositories.domain.FIRST_PAGINATION
import me.vitornascimento.trendingrepositories.domain.TrendingRepositoriesRepository
import me.vitornascimento.trendingrepositories.domain.model.EndOfPaginationException
import me.vitornascimento.trendingrepositories.domain.model.Failure
import me.vitornascimento.trendingrepositories.domain.model.Result
import me.vitornascimento.trendingrepositories.domain.model.Success
import me.vitornascimento.trendingrepositories.domain.model.TrendingRepository
import java.util.concurrent.TimeUnit

class TrendingRepositoriesRepositoryImpl(
    private val trendingRepositoriesDao: TrendingRepositoriesDao,
    private val service: GithubService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : TrendingRepositoriesRepository {

    override fun getTrendingRepositoriesForPage(page: Int): Flow<Result<List<TrendingRepository>>> =
        flow<Result<List<TrendingRepository>>> {
            val cachedTrendingRepositories = trendingRepositoriesDao.getTrendingRepositories()

            if (page == FIRST_PAGINATION && cacheIsOutOfDate(cachedTrendingRepositories)) {
                trendingRepositoriesDao.clearAllTrendingRepositories()
            }

            if (
                page == FIRST_PAGINATION &&
                cachedTrendingRepositories.isNotEmpty()
            ) {
                emit(
                    Success(
                        cachedTrendingRepositories
                            .map { it.toDomainModel() }
                    )
                )
            } else {
                val trendingRepositories = getTrendingRepositories(page)

                if (trendingRepositories.isEmpty()) {
                    throw EndOfPaginationException
                } else {
                    emit(
                        Success(trendingRepositories)
                    )
                }
            }
        }
            .catch { cause: Throwable ->
                emit(Failure(cause))
            }
            .flowOn(dispatcher)

    private fun cacheIsOutOfDate(cache: List<TrendingRepositoryEntity>): Boolean {
        val cacheTimeout: Long = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val createdAtFromFirstInCache: Long = cache.firstOrNull()?.createdAt ?: 0

        return System.currentTimeMillis() - createdAtFromFirstInCache < cacheTimeout
    }

    private suspend fun getTrendingRepositories(page: Int): List<TrendingRepository> {
        val trendingRepositories = service.getTrendingRepositories(page).toEntityModelList(page)
        trendingRepositoriesDao.insertAll(trendingRepositories)

        return trendingRepositories.map { it.toDomainModel() }
    }

}