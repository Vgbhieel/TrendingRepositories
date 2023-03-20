package me.vitornascimento.trendingrepositories.ui.component

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import me.vitornascimento.trendingrepositories.R
import me.vitornascimento.trendingrepositories.ui.tag.RepositoryStatsTags
import me.vitornascimento.trendingrepositories.ui.theme.TrendingRepositoriesTheme
import org.junit.Rule
import org.junit.Test

class RepositoryCardTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun whenComponentStartedVerifyRepositoryName() {
        val repositoryName = "My repository"

        initScreen(repositoryName = repositoryName)

        val repositoryNameLabel =
            composeTestRule.activity.getString(R.string.repository_name, repositoryName)
        composeTestRule.onNodeWithText(repositoryNameLabel).assertIsDisplayed()
    }

    @Test
    fun whenComponentStartedVerifyRepositoryAuthorName() {
        val repositoryAuthorUsername = "Foo apple"

        initScreen(authorUsername = repositoryAuthorUsername)

        val repositoryAuthorLabel =
            composeTestRule.activity.getString(R.string.repository_author, repositoryAuthorUsername)
        composeTestRule.onNodeWithText(repositoryAuthorLabel).assertIsDisplayed()
    }

    @Test
    fun whenComponentStartedVerifyRepositoryStatsIsDisplayed() {
        initScreen()

        composeTestRule.onNodeWithTag(RepositoryStatsTags.CONTENT).assertIsDisplayed()
    }

    private fun initScreen(
        repositoryName: String = "",
        authorUsername: String = "",
    ) {
        composeTestRule.setContent {
            TrendingRepositoriesTheme {
                RepositoryCard(
                    repositoryName = repositoryName,
                    authorUsername = authorUsername,
                    authorAvatarUrl = "",
                    starsCount = 0,
                    forksCount = 0,
                )
            }
        }
    }
}