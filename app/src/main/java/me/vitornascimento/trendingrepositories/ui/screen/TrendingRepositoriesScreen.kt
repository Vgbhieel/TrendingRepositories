package me.vitornascimento.trendingrepositories.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vitornascimento.trendingrepositories.R
import me.vitornascimento.trendingrepositories.ui.component.RepositoryCard
import me.vitornascimento.trendingrepositories.ui.component.TrendingRepositoriesTopAppBar
import me.vitornascimento.trendingrepositories.ui.theme.TrendingRepositoriesTheme

const val FAKE_LIST_COUNT = 10

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingRepositoriesScreen() {
    TrendingRepositoriesTheme {

        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
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
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxHeight()
            ) {
                items(FAKE_LIST_COUNT) {
                    RepositoryCard(
                        repositoryName = "Foo",
                        authorUsername = "FooUsername",
                        starsCount = 10,
                        forksCount = 1,
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrendingRepositoriesScreenPreview() {
    TrendingRepositoriesScreen()
}