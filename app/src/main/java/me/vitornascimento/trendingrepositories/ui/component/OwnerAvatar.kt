package me.vitornascimento.trendingrepositories.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import me.vitornascimento.trendingrepositories.R
import me.vitornascimento.trendingrepositories.ui.modifier.shimmer
import me.vitornascimento.trendingrepositories.ui.tag.OwnerAvatarTags.CONTENT

@Composable
fun OwnerAvatar(
    authorAvatarUrl: String,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .testTag(CONTENT),
    ) {
        var retryHash by remember { mutableStateOf(0) }
        val painter =
            rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(authorAvatarUrl)
                    .setParameter("retry_hash", retryHash, memoryCacheKey = null)
                    .build()
            )

        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = modifier
                        .shimmer(),
                )
            }
            is AsyncImagePainter.State.Error -> {
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(id = R.string.generic_error_message),
                        textAlign = TextAlign.Center,
                        lineHeight = 12.sp,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                    )
                    TextButton(
                        onClick = {
                            retryHash++
                        },
                    ) {
                        Text(
                            text = stringResource(id = R.string.retry),
                        )
                    }
                }
            }
            else -> {
                Image(
                    painter = painter,
                    contentDescription = stringResource(id = R.string.repository_card_avatar_content_description),
                    modifier = modifier,
                )
            }
        }
    }
}