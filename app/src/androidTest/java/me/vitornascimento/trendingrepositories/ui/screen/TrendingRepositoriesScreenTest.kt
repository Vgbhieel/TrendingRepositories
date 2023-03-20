package me.vitornascimento.trendingrepositories.ui.screen

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeUp
import io.mockk.mockk
import io.mockk.verify
import me.vitornascimento.trendingrepositories.domain.model.TrendingRepository
import me.vitornascimento.trendingrepositories.ui.tag.RepositoryCardTags
import me.vitornascimento.trendingrepositories.ui.tag.TrendingRepositoriesScreenTags.CONTENT_ERROR
import me.vitornascimento.trendingrepositories.ui.tag.TrendingRepositoriesScreenTags.CONTENT_LOADING
import me.vitornascimento.trendingrepositories.ui.tag.TrendingRepositoriesScreenTags.PAGINATION_ERROR
import me.vitornascimento.trendingrepositories.ui.tag.TrendingRepositoriesScreenTags.PAGINATION_EXHAUST
import me.vitornascimento.trendingrepositories.ui.tag.TrendingRepositoriesScreenTags.PAGINATION_LOADING
import me.vitornascimento.trendingrepositories.ui.tag.TrendingRepositoriesScreenTags.RETRY_BUTTON
import me.vitornascimento.trendingrepositories.ui.tag.TrendingRepositoriesScreenTags.TRENDING_REPOSITORIES_LIST
import me.vitornascimento.trendingrepositories.ui.tag.TrendingRepositoriesTopAppBarTags
import me.vitornascimento.trendingrepositories.ui.theme.TrendingRepositoriesTheme
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class TrendingRepositoriesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenUiStateWithEmptyTrendingRepositoriesAndPaginationStatusIdleWhenScreenStartedVerifyDoPaginationCall() {
        val doPagination: () -> Unit = mockk(relaxed = true)
        val uiState = TrendingRepositoriesScreenState(
            emptyList(),
            PaginationStatus.IDLE
        )

        initScreen(uiState = uiState, doPagination = doPagination)

        verify { doPagination.invoke() }
    }

    @Test
    fun whenUiStateHasARandomNumberOfTrendingRepositoriesVerifyUiContent() {
        val numberOfTrendingRepositories = Random.nextInt(0, 3)
        val uiState = TrendingRepositoriesScreenState(
            List(numberOfTrendingRepositories) { trendingRepositoryMock },
            PaginationStatus.IDLE
        )

        initScreen(uiState = uiState)

        composeTestRule.onAllNodesWithTag(RepositoryCardTags.CONTENT)
            .assertCountEquals(numberOfTrendingRepositories)
    }

    @Test
    fun givenUiStateWithARandomNumberOfTrendingRepositoriesWhenScrollingToEndOfListVerifyDoPaginationCall() {
        val numberOfTrendingRepositories = Random.nextInt(0, 20)
        val uiState = TrendingRepositoriesScreenState(
            List(numberOfTrendingRepositories) { trendingRepositoryMock },
            PaginationStatus.IDLE
        )
        val doPagination: () -> Unit = mockk(relaxed = true)

        initScreen(uiState = uiState, doPagination = doPagination)

        val lastIndex = numberOfTrendingRepositories - 1
        composeTestRule.onNodeWithTag(TRENDING_REPOSITORIES_LIST).performScrollToIndex(lastIndex)

        verify { doPagination.invoke() }
    }

    @Test
    fun whenScreenStartedShouldShowTopAppBar() {
        val uiState = TrendingRepositoriesScreenState(
            emptyList(),
            PaginationStatus.IDLE
        )

        initScreen(uiState = uiState)

        composeTestRule.onNodeWithTag(TrendingRepositoriesTopAppBarTags.CONTENT)
            .assertIsDisplayed()
    }

    @Test
    fun givenScrollAvailableWhenScrollingToEndOfListShouldNotShowTopAppBar() {
        val numberOfTrendingRepositories = 20
        val uiState = TrendingRepositoriesScreenState(
            List(numberOfTrendingRepositories) { trendingRepositoryMock },
            PaginationStatus.IDLE
        )

        initScreen(uiState = uiState)

        composeTestRule.onNodeWithTag(TRENDING_REPOSITORIES_LIST).performTouchInput {
            this.swipeUp()
        }

        composeTestRule.onNodeWithTag(TrendingRepositoriesTopAppBarTags.CONTENT)
            .assertIsNotDisplayed()
    }

    @Test
    fun givenScrollAvailableWhenScrollingToEndOfListAndScrollingToTopOfItThenShouldShowTopAppBarAgain() {
        val numberOfTrendingRepositories = 20
        val uiState = TrendingRepositoriesScreenState(
            List(numberOfTrendingRepositories) { trendingRepositoryMock },
            PaginationStatus.IDLE
        )

        initScreen(uiState = uiState)

        composeTestRule.onNodeWithTag(TRENDING_REPOSITORIES_LIST).performTouchInput {
            this.swipeUp()
        }

        composeTestRule.onNodeWithTag(TrendingRepositoriesTopAppBarTags.CONTENT)
            .assertIsNotDisplayed()

        composeTestRule.onNodeWithTag(TRENDING_REPOSITORIES_LIST).performTouchInput {
            this.swipeDown()
        }

        composeTestRule.onNodeWithTag(TrendingRepositoriesTopAppBarTags.CONTENT)
            .assertIsDisplayed()
    }

    @Test
    fun whenUiStateIsLoadingVerifyUiContent() {
        val uiState = TrendingRepositoriesScreenState(
            emptyList(),
            PaginationStatus.LOADING
        )

        initScreen(uiState = uiState)

        composeTestRule.onAllNodesWithTag(CONTENT_LOADING).assertCountEquals(20)
    }

    @Test
    fun whenUiStateIsErrorVerifyUiContent() {
        val uiState = TrendingRepositoriesScreenState(
            emptyList(),
            PaginationStatus.ERROR
        )

        initScreen(uiState = uiState)

        composeTestRule.onNodeWithTag(CONTENT_ERROR).assertIsDisplayed()
    }

    @Test
    fun givenUiStateErrorWhenRetryButtonClickedVerifyDoPaginationCall() {
        val uiState = TrendingRepositoriesScreenState(
            emptyList(),
            PaginationStatus.ERROR
        )
        val doPagination: () -> Unit = mockk(relaxed = true)

        initScreen(uiState = uiState, doPagination = doPagination)

        composeTestRule.onNodeWithTag(RETRY_BUTTON).performClick()

        verify { doPagination.invoke() }
    }

    @Test
    fun whenUiStateIsPaginatingVerifyUiContent() {
        val uiState = TrendingRepositoriesScreenState(
            List(2) { trendingRepositoryMock },
            PaginationStatus.PAGINATING
        )

        initScreen(uiState = uiState)

        composeTestRule.onNodeWithTag(PAGINATION_LOADING).assertIsDisplayed()
    }

    @Test
    fun whenUiStateIsPaginatingErrorVerifyUiContent() {
        val uiState = TrendingRepositoriesScreenState(
            List(2) { trendingRepositoryMock },
            PaginationStatus.PAGINATING_ERROR
        )

        initScreen(uiState = uiState)

        composeTestRule.onNodeWithTag(PAGINATION_ERROR).assertIsDisplayed()
    }

    @Test
    fun givenUiStatePaginatingErrorWhenRetryButtonClickedVerifyDoPaginationCall() {
        val uiState = TrendingRepositoriesScreenState(
            List(2) { trendingRepositoryMock },
            PaginationStatus.PAGINATING_ERROR
        )
        val doPagination: () -> Unit = mockk(relaxed = true)

        initScreen(uiState = uiState, doPagination = doPagination)

        composeTestRule.onNodeWithTag(RETRY_BUTTON).performClick()

        verify { doPagination.invoke() }
    }

    @Test
    fun whenUiStateIsPaginationExhaustVerifyUiContent() {
        val uiState = TrendingRepositoriesScreenState(
            List(2) { trendingRepositoryMock },
            PaginationStatus.PAGINATION_EXHAUST
        )

        initScreen(uiState = uiState)

        composeTestRule.onNodeWithTag(PAGINATION_EXHAUST).assertIsDisplayed()
    }

    private fun initScreen(
        uiState: TrendingRepositoriesScreenState,
        doPagination: (() -> Unit)? = null
    ) {
        composeTestRule.setContent {
            TrendingRepositoriesTheme {
                TrendingRepositoriesScreen(
                    uiState = uiState,
                    doPagination = doPagination ?: {},
                )
            }
        }
    }

    private companion object {
        val trendingRepositoryMock: TrendingRepository = TrendingRepository(
            name = "Foo",
            starsCount = 10,
            forksCount = 1,
            ownerUsername = "foo",
            ownerAvatarUrl = "",
        )
    }
}