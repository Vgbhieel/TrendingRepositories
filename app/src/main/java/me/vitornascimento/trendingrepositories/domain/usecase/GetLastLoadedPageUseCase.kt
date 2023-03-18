package me.vitornascimento.trendingrepositories.domain.usecase

import me.vitornascimento.trendingrepositories.domain.repository.TrendingRepositoriesRepository
import javax.inject.Inject

class GetLastLoadedPageUseCase @Inject constructor(
    private val trendingRepositoriesRepository: TrendingRepositoriesRepository
) {

    suspend operator fun invoke(): Int = trendingRepositoriesRepository.getLastLoadedPage()
}