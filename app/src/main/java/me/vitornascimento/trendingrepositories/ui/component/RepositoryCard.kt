package me.vitornascimento.trendingrepositories.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vitornascimento.trendingrepositories.R
import me.vitornascimento.trendingrepositories.ui.modifier.shimmer
import me.vitornascimento.trendingrepositories.ui.tag.RepositoryCardTags.CONTENT

const val PREVIEW_STARS_COUNT = 10
const val PREVIEW_FORKS_COUNT = 1

@Composable
fun RepositoryCard(
    repositoryName: String,
    authorUsername: String,
    authorAvatarUrl: String,
    starsCount: Int,
    forksCount: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .testTag(CONTENT),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            OwnerAvatar(
                authorAvatarUrl = authorAvatarUrl,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                    .width(125.dp)
                    .height(125.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 8.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    stringResource(id = R.string.repository_name, repositoryName),
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    stringResource(id = R.string.repository_author, authorUsername),
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(4.dp))

                RepositoryStats(
                    starsCount = starsCount,
                    forksCount = forksCount,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
fun RepositoryCardLoading(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shimmer()
        ) {
            Box(modifier = Modifier.height(125.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RepositoryCardPreview() {
    RepositoryCard(
        repositoryName = "TrendingRepositories",
        authorUsername = "Vgbhieel",
        authorAvatarUrl = "foo",
        starsCount = PREVIEW_STARS_COUNT,
        forksCount = PREVIEW_FORKS_COUNT,
    )
}