package me.vitornascimento.trendingrepositories.domain

import kotlinx.coroutines.flow.Flow
import me.vitornascimento.trendingrepositories.domain.model.Result
import me.vitornascimento.trendingrepositories.domain.model.TrendingRepository

const val FIRST_PAGINATION = 1

interface TrendingRepositoriesRepository {

    fun getTrendingRepositoriesForPage(page: Int): Flow<Result<List<TrendingRepository>>>
}