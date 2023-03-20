package me.vitornascimento.trendingrepositories.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.vitornascimento.trendingrepositories.R
import me.vitornascimento.trendingrepositories.domain.model.TrendingRepository
import me.vitornascimento.trendingrepositories.ui.component.RepositoryCard
import me.vitornascimento.trendingrepositories.ui.component.RepositoryCardLoading
import me.vitornascimento.trendingrepositories.ui.component.TrendingRepositoriesTopAppBar
import me.vitornascimento.trendingrepositories.ui.theme.TrendingRepositoriesTheme

private const val DEFAULT_LAST_INDEX = -2
private const val DEFAULT_PAGINATION_DECREMENT = 2
private const val CONTENT_LOADING_REPEAT_TIMES = 20

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingRepositoriesScreen(
    uiState: TrendingRepositoriesScreenState,
    doPagination: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val lazyColumnListState = rememberLazyListState()
    val shouldStartPaginate = remember {
        derivedStateOf {
            (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: DEFAULT_LAST_INDEX) >= (lazyColumnListState.layoutInfo.totalItemsCount
                    - DEFAULT_PAGINATION_DECREMENT)
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (
            shouldStartPaginate.value &&
            uiState.paginationStatus == PaginationStatus.IDLE
        )
            doPagination.invoke()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TrendingRepositoriesTopAppBar(
                stringResource(id = R.string.app_name),
                scrollBehavior
            )
        },
    ) { paddingValues: PaddingValues ->

        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            state = lazyColumnListState,
        ) {
            items(
                count = uiState.trendingRepositories.size,
            ) {
                val trendingRepository = uiState.trendingRepositories[it]
                RepositoryCard(
                    repositoryName = trendingRepository.name,
                    authorUsername = trendingRepository.ownerUsername,
                    authorAvatarUrl = trendingRepository.ownerAvatarUrl,
                    starsCount = trendingRepository.starsCount,
                    forksCount = trendingRepository.starsCount,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                )
            }

            item {
                when (uiState.paginationStatus) {
                    PaginationStatus.LOADING -> {
                        ContentLoading()
                    }
                    PaginationStatus.PAGINATING -> {
                        PaginationLoading()
                    }
                    PaginationStatus.PAGINATING_ERROR -> {
                        PaginationError {
                            doPagination.invoke()
                        }
                    }
                    PaginationStatus.PAGINATION_EXHAUST -> {
                        PaginationExhaust()
                    }
                    else -> {
                        //do nothing
                    }
                }
            }
        }

        if (uiState.paginationStatus == PaginationStatus.ERROR) {
            ContentError {
                doPagination()
            }
        }
    }
}

@Composable
fun ContentLoading() {
    repeat(CONTENT_LOADING_REPEAT_TIMES) {
        RepositoryCardLoading(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun PaginationLoading() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun GenericError(
    modifier: Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    onRetryClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(id = R.string.generic_error_message),
            fontSize = fontSize,
            textAlign = TextAlign.Center,
        )
        Spacer(
            modifier = Modifier
                .padding(top = 8.dp)
        )
        Button(onClick = onRetryClick) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
fun ContentError(onRetryClick: () -> Unit) {
    GenericError(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxSize(),
        fontSize = 18.sp,
        onRetryClick = onRetryClick,
    )
}

@Composable
fun PaginationError(onRetryClick: () -> Unit) {
    GenericError(
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        onRetryClick = onRetryClick,
    )
}

@Composable
fun PaginationExhaust() {
    Text(text = "It looks like we get to the end.")
}

@Preview(showBackground = true)
@Composable
fun TrendingRepositoriesScreenPreview() {
    TrendingRepositoriesTheme {
        TrendingRepositoriesScreen(
            uiState = TrendingRepositoriesScreenState(
                trendingRepositories = listOf(
                    TrendingRepository(
                        name = "Foo name",
                        starsCount = 1,
                        forksCount = 2,
                        ownerUsername = "Foo username",
                        ownerAvatarUrl = "",
                    ),
                    TrendingRepository(
                        name = "Foo name 2",
                        starsCount = 10,
                        forksCount = 0,
                        ownerUsername = "Foo username 2",
                        ownerAvatarUrl = "",
                    ),
                ),
                paginationStatus = PaginationStatus.IDLE,
            ),
            doPagination = {}
        )
    }
}