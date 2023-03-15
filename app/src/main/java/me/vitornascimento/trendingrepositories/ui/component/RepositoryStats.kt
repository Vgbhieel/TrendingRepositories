package me.vitornascimento.trendingrepositories.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vitornascimento.trendingrepositories.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RepositoryStats(
    starsCount: Int,
    forksCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_star),
            contentDescription = null,
        )
        Text(
            text = pluralStringResource(id = R.plurals.repository_stars, starsCount, starsCount),
            modifier = Modifier.padding(start = 4.dp),
        )

        Spacer(
            modifier = Modifier
                .width(8.dp)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_fork),
            contentDescription = null,
        )
        Text(
            text = pluralStringResource(id = R.plurals.repository_forks, forksCount, forksCount),
            modifier = Modifier
                .padding(start = 4.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositoryStatsPreview() {
    RepositoryStats(
        starsCount = 1,
        forksCount = 2,
    )
}