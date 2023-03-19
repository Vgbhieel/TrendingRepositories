package me.vitornascimento.trendingrepositories.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import me.vitornascimento.trendingrepositories.data.local.dao.TrendingRepositoriesDao
import me.vitornascimento.trendingrepositories.data.local.entity.TrendingRepositoryEntity
import me.vitornascimento.trendingrepositories.data.mapper.toDomainModel
import me.vitornascimento.trendingrepositories.data.mapper.toEntityModelList
import me.vitornascimento.trendingrepositories.data.remote.GithubService
import me.vitornascimento.trendingrepositories.data.remote.dto.RepositoryOwnerInfoDTO
import me.vitornascimento.trendingrepositories.data.remote.dto.TrendingRepositoriesResponse
import me.vitornascimento.trendingrepositories.data.remote.dto.TrendingRepositoryDTO
import me.vitornascimento.trendingrepositories.domain.model.EndOfPaginationException
import me.vitornascimento.trendingrepositories.domain.model.Failure
import me.vitornascimento.trendingrepositories.domain.model.Success
import me.vitornascimento.trendingrepositories.domain.model.TrendingRepository
import org.junit.After
import org.junit.Test
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class TrendingRepositoriesRepositoryImplTest {

    private val trendingRepositoriesDao: TrendingRepositoriesDao = mockk(relaxed = true)
    private val service: GithubService = mockk(relaxed = true)
    private val dispatcher = StandardTestDispatcher()

    private val repositoryImpl = TrendingRepositoriesRepositoryImpl(
        trendingRepositoriesDao = trendingRepositoriesDao,
        service = service,
        dispatcher = dispatcher,
    )

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `whenever getLastLoadedPage called should call dao`() = runTest(context = dispatcher) {
        repositoryImpl.getLastLoadedPage()

        coVerify {
            trendingRepositoriesDao.getLastPageLoaded()
        }
    }

    @Test
    fun `when getLastLoadedPage called should return 0`() = runTest(context = dispatcher) {
        coEvery { trendingRepositoriesDao.getLastPageLoaded() } returns null

        val result = repositoryImpl.getLastLoadedPage()

        assertEquals(0, result)
    }

    @Test
    fun `when getLastLoadedPage called verify its return`() = runTest(context = dispatcher) {
        val lastLoadedPage = Random.nextInt()

        coEvery { trendingRepositoriesDao.getLastPageLoaded() } returns lastLoadedPage

        val result = repositoryImpl.getLastLoadedPage()

        assertEquals(lastLoadedPage, result)
    }

    @Test
    fun `given any exception when getTrendingRepositoriesForPage called should return error`() =
        runTest(context = dispatcher) {
            coEvery { trendingRepositoriesDao.getTrendingRepositories() } throws Exception()

            val result = repositoryImpl.getTrendingRepositoriesForPage(1).single()

            assertEquals(Failure::class, result::class)
        }

    @Test
    fun `given exception when getTrendingRepositoriesForPage called verify error data`() =
        runTest(context = dispatcher) {
            val exception = Exception("Fake exception")
            coEvery { trendingRepositoriesDao.getTrendingRepositories() } throws exception

            val result = repositoryImpl.getTrendingRepositoriesForPage(1).single()

            result as Failure
            assertEquals(exception, result.cause)
        }

    @Test
    fun `given any page when getTrendingRepositoriesForPage called should return success`() =
        runTest(context = dispatcher) {
            val page = Random.nextInt()

            coEvery {
                service.getTrendingRepositories(page)
            } returns TrendingRepositoriesResponse(DEFAULT_SERVICE_DATA)

            val result = repositoryImpl.getTrendingRepositoriesForPage(page).single()

            assertEquals(Success::class, result::class)
        }

    @Test
    fun `given page 1 and cached data when getTrendingRepositoriesForPage called verify success data`() =
        runTest(context = dispatcher) {
            val page = 1
            val cachedData: List<TrendingRepositoryEntity> = DEFAULT_CACHED_DATA

            coEvery { trendingRepositoriesDao.getTrendingRepositories() } returns cachedData

            val expectedResult = cachedData.map { it.toDomainModel() }
            val result = repositoryImpl.getTrendingRepositoriesForPage(page)
                .single() as Success<List<TrendingRepository>>

            assertEquals(expectedResult, result.data)
        }

    @Test
    fun `given page 1 and cached data out of date when getTrendingRepositoriesForPage called verify cache cleared`() =
        runTest(context = dispatcher) {
            val page = 1
            val cachedData: List<TrendingRepositoryEntity> = OUT_OF_DATE_CACHED_DATA

            coEvery { trendingRepositoriesDao.getTrendingRepositories() } returns cachedData

            repositoryImpl.getTrendingRepositoriesForPage(page).single()

            coVerify { trendingRepositoriesDao.clearAllTrendingRepositories() }
        }

    @Test
    fun `given page 1 and no cached data when getTrendingRepositoriesForPage called verify service call`() =
        runTest(context = dispatcher) {
            val page = 1

            coEvery { trendingRepositoriesDao.getTrendingRepositories() } returns emptyList()

            repositoryImpl.getTrendingRepositoriesForPage(page).single()

            coVerify { service.getTrendingRepositories(page) }
        }

    @Test
    fun `given any page other than 1 when getTrendingRepositoriesForPage called verify service call`() =
        runTest(context = dispatcher) {
            val page = Random.nextInt(from = 2, until = 1001)

            coEvery { trendingRepositoriesDao.getTrendingRepositories() } returns DEFAULT_CACHED_DATA

            repositoryImpl.getTrendingRepositoriesForPage(page).single()

            coVerify { service.getTrendingRepositories(page) }
        }

    @Test
    fun `given any page other than 1 when getTrendingRepositoriesForPage called verify success data`() =
        runTest(context = dispatcher) {
            val page = Random.nextInt(from = 2, until = 1001)
            val response = TrendingRepositoriesResponse(
                DEFAULT_SERVICE_DATA
            )

            coEvery { service.getTrendingRepositories(page) } returns response

            val expectedResult = response.toEntityModelList(page).map { it.toDomainModel() }

            val result = repositoryImpl.getTrendingRepositoriesForPage(page).single() as Success

            assertEquals(expectedResult, result.data)
        }

    @Test
    fun `given empty service return when getTrendingRepositoriesForPage called verify error data`() =
        runTest(context = dispatcher) {
            val page = Random.nextInt(from = 2, until = 1001)
            val response = TrendingRepositoriesResponse(
                emptyList()
            )

            coEvery { service.getTrendingRepositories(page) } returns response

            val result = repositoryImpl.getTrendingRepositoriesForPage(page).single() as Failure

            assertEquals(EndOfPaginationException, result.cause)
        }

    private companion object {
        val DEFAULT_CACHED_DATA: List<TrendingRepositoryEntity> = listOf(
            TrendingRepositoryEntity(
                id = 1,
                name = "foo",
                starsCount = 1,
                forksCount = 1,
                ownerUsername = "foo",
                ownerAvatarUrl = "foo",
                page = 1,
            )
        )

        val OUT_OF_DATE_CACHED_DATA: List<TrendingRepositoryEntity> = listOf(
            TrendingRepositoryEntity(
                id = 1,
                name = "foo",
                starsCount = 1,
                forksCount = 1,
                ownerUsername = "foo",
                ownerAvatarUrl = "foo",
                page = 1,
                createdAt = 0,
            )
        )

        val DEFAULT_SERVICE_DATA: List<TrendingRepositoryDTO> = listOf(
            TrendingRepositoryDTO(
                id = 1,
                name = "foo",
                starsCount = 1,
                forksCount = 1,
                ownerInfo = RepositoryOwnerInfoDTO(
                    username = "foo",
                    avatarUrl = "foo"
                ),
            )
        )
    }
}