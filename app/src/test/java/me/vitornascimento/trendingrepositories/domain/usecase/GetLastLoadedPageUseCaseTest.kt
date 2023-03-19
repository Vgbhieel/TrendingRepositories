package me.vitornascimento.trendingrepositories.domain.usecase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.vitornascimento.trendingrepositories.domain.repository.TrendingRepositoriesRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class GetLastLoadedPageUseCaseTest {

    private val repository: TrendingRepositoriesRepository = mockk()
    private val useCase = GetLastLoadedPageUseCase(repository)

    @Test
    fun test() = runTest {
        val page = Random.nextInt()

        coEvery { repository.getLastLoadedPage() } returns page

        val result = useCase()

        assertEquals(page, result)
    }
}