package me.vitornascimento.trendingrepositories.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import me.vitornascimento.trendingrepositories.domain.model.EndOfPaginationException
import me.vitornascimento.trendingrepositories.domain.model.Failure
import me.vitornascimento.trendingrepositories.domain.model.Success
import me.vitornascimento.trendingrepositories.domain.model.TrendingRepository
import me.vitornascimento.trendingrepositories.domain.repository.FIRST_PAGINATION
import me.vitornascimento.trendingrepositories.domain.usecase.GetLastLoadedPageUseCase
import me.vitornascimento.trendingrepositories.domain.usecase.GetTrendingRepositoriesUseCase
import me.vitornascimento.trendingrepositories.rule.MainDispatcherRule
import me.vitornascimento.trendingrepositories.ui.screen.PaginationStatus
import me.vitornascimento.trendingrepositories.ui.screen.TrendingRepositoriesScreenState
import me.vitornascimento.trendingrepositories.ui.viewmodel.TrendingRepositoriesViewModel.Companion.UI_STATE_KEY
import org.junit.After
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class TrendingRepositoriesViewModelTest {

    private val getTrendingRepositoriesUseCase: GetTrendingRepositoriesUseCase =
        mockk(relaxed = true)
    private val getLastLoadedPageUseCase: GetLastLoadedPageUseCase = mockk(relaxed = true)

    private lateinit var viewModel: TrendingRepositoriesViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when viewModel created verify uiState`() {
        initViewModel()

        val expectedUiState = TrendingRepositoriesScreenState(
            emptyList(),
            PaginationStatus.IDLE
        )

        assertEquals(expectedUiState, viewModel.uiState.value)
    }

    @Test
    fun `given saved state handle when viewModel created verify uiState`() {
        val expectedUiState = TrendingRepositoriesScreenState(
            listOf(
                TrendingRepository(
                    name = "foo",
                    starsCount = 1,
                    forksCount = 1,
                    ownerUsername = "",
                    ownerAvatarUrl = ""
                )
            ),
            PaginationStatus.PAGINATING_ERROR
        )

        initViewModel(expectedUiState)

        assertEquals(expectedUiState, viewModel.uiState.value)
    }

    @Test
    fun `when viewModel doPagination called for the first time verify getTrendingRepositoriesUseCase call`() {
        initViewModel()

        viewModel.doPagination()

        coVerify { getTrendingRepositoriesUseCase(FIRST_PAGINATION) }
    }

    @Test
    fun `when viewModel doPagination called for the first time verify getLastLoadedPageUseCase call`() {
        initViewModel()

        viewModel.doPagination()

        coVerify(exactly = NEVER) { getLastLoadedPageUseCase() }
    }

    @Test
    fun `when viewModel doPagination called for other than the first time verify getTrendingRepositoriesUseCase call`() {
        initViewModel()

        coEvery { getTrendingRepositoriesUseCase(any()) } returns flowOf(
            Success(
                DEFAULT_TRENDING_REPOSITORIES_DATA
            )
        )

        viewModel.doPagination()
        viewModel.doPagination()

        coVerify { getLastLoadedPageUseCase() }
    }

    @Test
    fun `when viewModel doPagination called verify success uiState`() {
        initViewModel()

        coEvery { getTrendingRepositoriesUseCase(any()) } returns flowOf(
            Success(
                DEFAULT_TRENDING_REPOSITORIES_DATA
            )
        )

        val expectedResult = TrendingRepositoriesScreenState(
            DEFAULT_TRENDING_REPOSITORIES_DATA,
            PaginationStatus.IDLE,
        )

        viewModel.doPagination()

        assertEquals(expectedResult, viewModel.uiState.value)
    }

    @Test
    fun `when viewModel doPagination called verify error uiState for first pagination`() {
        initViewModel()

        coEvery { getTrendingRepositoriesUseCase(any()) } returns flowOf(
            Failure(Exception())
        )

        val expectedResult = TrendingRepositoriesScreenState(
            emptyList(),
            PaginationStatus.ERROR,
        )

        viewModel.doPagination()

        assertEquals(expectedResult, viewModel.uiState.value)
    }

    @Test
    fun `when viewModel doPagination called verify error uiState for other than first pagination`() {
        initViewModel()

        coEvery { getTrendingRepositoriesUseCase(any()) } returns flowOf(
            Success(DEFAULT_TRENDING_REPOSITORIES_DATA)
        )
        viewModel.doPagination()

        val page = Random.nextInt(1, 1000)
        coEvery { getLastLoadedPageUseCase() } returns page
        coEvery { getTrendingRepositoriesUseCase(any()) } returns flowOf(
            Failure(Exception())
        )
        viewModel.doPagination()

        val expectedResult = TrendingRepositoriesScreenState(
            DEFAULT_TRENDING_REPOSITORIES_DATA,
            PaginationStatus.PAGINATING_ERROR,
        )

        assertEquals(expectedResult, viewModel.uiState.value)
    }

    @Test
    fun `given pagination exhaust when viewModel doPagination called verify error uiState`() {
        initViewModel()

        coEvery { getTrendingRepositoriesUseCase(any()) } returns flowOf(
            Failure(EndOfPaginationException)
        )
        viewModel.doPagination()

        val expectedResult = TrendingRepositoriesScreenState(
            emptyList(),
            PaginationStatus.PAGINATION_EXHAUST,
        )

        assertEquals(expectedResult, viewModel.uiState.value)
    }

    private fun initViewModel(uiState: TrendingRepositoriesScreenState? = null) {
        viewModel = TrendingRepositoriesViewModel(
            getTrendingRepositoriesUseCase = getTrendingRepositoriesUseCase,
            getLastLoadedPageUseCase = getLastLoadedPageUseCase,
            savedStateHandle = SavedStateHandle(
                uiState?.let { mapOf(UI_STATE_KEY to it) } ?: mapOf()
            ),
        )
    }

    private companion object {
        const val NEVER = 0
        val DEFAULT_TRENDING_REPOSITORIES_DATA = listOf(
            TrendingRepository(
                name = "foo",
                starsCount = 1,
                forksCount = 1,
                ownerUsername = "foo",
                ownerAvatarUrl = "foo",
            )
        )
    }
}