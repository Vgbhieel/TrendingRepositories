package me.vitornascimento.trendingrepositories.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import me.vitornascimento.trendingrepositories.ui.screen.TrendingRepositoriesScreen
import me.vitornascimento.trendingrepositories.ui.theme.TrendingRepositoriesTheme
import me.vitornascimento.trendingrepositories.ui.viewmodel.TrendingRepositoriesViewModel

@AndroidEntryPoint
class TrendingRepositoriesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrendingRepositoriesTheme {
                val viewModel: TrendingRepositoriesViewModel = viewModel()
                val uiState = viewModel.uiState.collectAsState()

                TrendingRepositoriesScreen(
                    uiState = uiState.value,
                    doPagination = { viewModel.doPagination() }
                )
            }
        }
    }
}