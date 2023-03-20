package me.vitornascimento.trendingrepositories.ui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import me.vitornascimento.trendingrepositories.ui.tag.TrendingRepositoriesTopAppBarTags.CONTENT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingRepositoriesTopAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(),
        scrollBehavior = scrollBehavior,
        modifier = Modifier
            .testTag(CONTENT),
    )
}