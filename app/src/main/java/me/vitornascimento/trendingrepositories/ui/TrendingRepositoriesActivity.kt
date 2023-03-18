package me.vitornascimento.trendingrepositories.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import me.vitornascimento.trendingrepositories.ui.screen.TrendingRepositoriesScreen

@AndroidEntryPoint
class TrendingRepositoriesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrendingRepositoriesScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TrendingRepositoriesScreen()
}