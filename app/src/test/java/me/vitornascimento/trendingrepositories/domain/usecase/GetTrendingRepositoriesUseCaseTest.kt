package me.vitornascimento.trendingrepositories.domain.usecase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import me.vitornascimento.trendingrepositories.domain.model.Success
import me.vitornascimento.trendingrepositories.domain.model.TrendingRepository
import me.vitornascimento.trendingrepositories.domain.repository.TrendingRepositoriesRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class GetTrendingRepositoriesUseCaseTest {

    private val repository: TrendingRepositoriesRepository = mockk()
    private val useCase = GetTrendingRepositoriesUseCase(repository)

    @Test
    fun test() = runTest {
        val page = Random.nextInt()
        val trendingRepositoriesList: List<TrendingRepository> = listOf(
            mockk()
        )

        coEvery { repository.getTrendingRepositoriesForPage(page) } returns flowOf(
            Success(trendingRepositoriesList)
        )


        val expectedResult = Success(trendingRepositoriesList)
        val result = useCase(page).single()

        assertEquals(expectedResult, result)
    }
}