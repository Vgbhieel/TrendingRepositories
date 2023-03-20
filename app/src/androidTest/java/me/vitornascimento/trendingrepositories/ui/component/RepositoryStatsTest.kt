package me.vitornascimento.trendingrepositories.ui.component

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import me.vitornascimento.trendingrepositories.R
import me.vitornascimento.trendingrepositories.ui.tag.RepositoryStatsTags.FORK_ICON
import me.vitornascimento.trendingrepositories.ui.tag.RepositoryStatsTags.STAR_ICON
import me.vitornascimento.trendingrepositories.ui.theme.TrendingRepositoriesTheme
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class RepositoryStatsTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun whenComponentStartedVerifyStarsCount() {
        val starsCount = Random.nextInt(0, 10)

        initScreen(starsCount = starsCount)

        val context = composeTestRule.activity as Context
        val starsCountLabel =
            context.resources.getQuantityString(R.plurals.repository_stars, starsCount, starsCount)

        composeTestRule.onNodeWithText(starsCountLabel).assertIsDisplayed()
    }

    @Test
    fun whenComponentStartedVerifyStarIconVisibility() {
        initScreen()

        composeTestRule.onNodeWithTag(STAR_ICON).assertIsDisplayed()
    }

    @Test
    fun whenComponentStartedVerifyForksCount() {
        val forksCount = Random.nextInt(0, 10)

        initScreen(forksCount = forksCount)

        val context = composeTestRule.activity as Context
        val forksCountLabel =
            context.resources.getQuantityString(R.plurals.repository_forks, forksCount, forksCount)

        composeTestRule.onNodeWithText(forksCountLabel).assertIsDisplayed()
    }

    @Test
    fun whenComponentStartedVerifyForkIconVisibility() {
        initScreen()

        composeTestRule.onNodeWithTag(FORK_ICON).assertIsDisplayed()
    }

    private fun initScreen(
        starsCount: Int = 0,
        forksCount: Int = 0,
    ) {
        composeTestRule.setContent {
            TrendingRepositoriesTheme {
                RepositoryStats(
                    starsCount = starsCount,
                    forksCount = forksCount,
                )
            }
        }
    }
}