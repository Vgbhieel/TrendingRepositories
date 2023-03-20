package me.vitornascimento.trendingrepositories.ui.component

import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import me.vitornascimento.trendingrepositories.ui.theme.TrendingRepositoriesTheme
import org.junit.Rule
import org.junit.Test

class TrendingRepositoriesTopAppBarTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    @OptIn(ExperimentalMaterial3Api::class)
    fun whenComponentStartedVerifyItsTitle() {
        val title = "foo"

        composeTestRule.setContent {
            TrendingRepositoriesTheme {
                TrendingRepositoriesTopAppBar(title)
            }
        }

        composeTestRule.onNodeWithText(title).assertIsDisplayed()
    }
}