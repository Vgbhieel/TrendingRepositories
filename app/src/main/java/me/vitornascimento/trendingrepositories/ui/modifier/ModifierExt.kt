package me.vitornascimento.trendingrepositories.ui.modifier

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

private const val TWO_NEGATIVE = -2
private const val TWO = 2
private const val ONE_SECOND_IN_MILLIS = 1_000

fun Modifier.shimmer(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = TWO_NEGATIVE * size.width.toFloat(),
        targetValue = TWO * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(ONE_SECOND_IN_MILLIS)
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color.LightGray,
                Color.Gray,
                Color.LightGray,
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}