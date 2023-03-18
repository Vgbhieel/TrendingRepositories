package me.vitornascimento.trendingrepositories.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.vitornascimento.trendingrepositories.domain.model.Result
import me.vitornascimento.trendingrepositories.domain.model.TrendingRepository
import me.vitornascimento.trendingrepositories.domain.repository.TrendingRepositoriesRepository
import javax.inject.Inject

class GetTrendingRepositoriesUseCase @Inject constructor(
    private val trendingRepositoriesRepository: TrendingRepositoriesRepository
) {

    operator fun invoke(page: Int): Flow<Result<List<TrendingRepository>>> =
        trendingRepositoriesRepository.getTrendingRepositoriesForPage(page)
}