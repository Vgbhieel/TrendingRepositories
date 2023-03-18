package me.vitornascimento.trendingrepositories.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.vitornascimento.trendingrepositories.domain.model.EndOfPaginationException
import me.vitornascimento.trendingrepositories.domain.model.Failure
import me.vitornascimento.trendingrepositories.domain.model.Success
import me.vitornascimento.trendingrepositories.domain.repository.FIRST_PAGINATION
import me.vitornascimento.trendingrepositories.domain.usecase.GetLastLoadedPageUseCase
import me.vitornascimento.trendingrepositories.domain.usecase.GetTrendingRepositoriesUseCase
import me.vitornascimento.trendingrepositories.ui.screen.PaginationStatus
import me.vitornascimento.trendingrepositories.ui.screen.TrendingRepositoriesScreenState
import javax.inject.Inject

@HiltViewModel
class TrendingRepositoriesViewModel @Inject constructor(
    private val getTrendingRepositoriesUseCase: GetTrendingRepositoriesUseCase,
    private val getLastLoadedPageUseCase: GetLastLoadedPageUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val uiState: StateFlow<TrendingRepositoriesScreenState> =
        savedStateHandle.getStateFlow(
            UI_STATE_KEY,
            TrendingRepositoriesScreenState(
                trendingRepositories = listOf(),
                paginationStatus = PaginationStatus.IDLE,
            )
        )

    private var isFirstPagination: Boolean = true

    fun doPagination() {
        viewModelScope.launch {
            val oldUiState: TrendingRepositoriesScreenState = uiState.value

            val actualPage: Int = if (isFirstPagination) {
                savedStateHandle[UI_STATE_KEY] = oldUiState.copy(
                    paginationStatus = PaginationStatus.LOADING
                )

                FIRST_PAGINATION
            } else {
                savedStateHandle[UI_STATE_KEY] = oldUiState.copy(
                    paginationStatus = PaginationStatus.PAGINATING
                )

                getLastLoadedPageUseCase() + 1
            }

            getTrendingRepositoriesUseCase(actualPage).map {
                when (it) {
                    is Success -> {
                        isFirstPagination = false
                        oldUiState.copy(
                            trendingRepositories = oldUiState.trendingRepositories + it.data,
                            paginationStatus = PaginationStatus.IDLE,
                        )
                    }
                    is Failure -> {
                        if (it.cause is EndOfPaginationException) {
                            oldUiState.copy(
                                trendingRepositories = oldUiState.trendingRepositories,
                                paginationStatus = PaginationStatus.PAGINATION_EXHAUST,
                            )
                        } else {
                            if (actualPage == FIRST_PAGINATION) {
                                oldUiState.copy(
                                    paginationStatus = PaginationStatus.ERROR,
                                )
                            } else {
                                oldUiState.copy(
                                    paginationStatus = PaginationStatus.PAGINATING_ERROR,
                                )
                            }
                        }
                    }
                }
            }.collect { newUiSate: TrendingRepositoriesScreenState ->
                savedStateHandle[UI_STATE_KEY] = newUiSate
            }
        }
    }

    private companion object {
        const val UI_STATE_KEY = "ui_state"
    }
}