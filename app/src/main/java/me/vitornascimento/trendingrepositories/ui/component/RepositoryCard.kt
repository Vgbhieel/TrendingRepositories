package me.vitornascimento.trendingrepositories.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vitornascimento.trendingrepositories.R

const val PREVIEW_STARS_COUNT = 10
const val PREVIEW_FORKS_COUNT = 1

@Composable
fun RepositoryCard(
    repositoryName: String,
    authorUsername: String,
    starsCount: Int,
    forksCount: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

            Image(
                painter = painterResource(R.drawable.ic_star),
                contentDescription = stringResource(id = R.string.repository_card_avatar_content_description),
                modifier = Modifier
                    .width(125.dp)
                    .height(125.dp)
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    stringResource(id = R.string.repository_name, repositoryName)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    stringResource(id = R.string.repository_author, authorUsername)
                )

                Spacer(modifier = Modifier.height(4.dp))

                RepositoryStats(
                    starsCount = starsCount,
                    forksCount = forksCount,
                    modifier = Modifier
                        .padding(top = 8.dp),
                )
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun RepositoryCardPreview() {
    RepositoryCard(
        repositoryName = "TrendingRepositories",
        authorUsername = "Vgbhieel",
        starsCount = PREVIEW_STARS_COUNT,
        forksCount = PREVIEW_FORKS_COUNT,
    )
}