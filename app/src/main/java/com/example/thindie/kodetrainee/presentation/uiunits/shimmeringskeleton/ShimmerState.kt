package com.example.thindie.kodetrainee.presentation.uiunits.shimmeringskeleton

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

@Stable
class ShimmerState(
    private val isWideScreenSize: Boolean,
    private val transition: InfiniteTransition,
) {
    private val color
        @Composable get() = MaterialTheme.colorScheme.surfaceVariant
    private val shimmersColorsList
        @Composable get() = listOf(
            color.copy(0.6f),
            color.copy(0.2f),
            color.copy(0.6f)
        )

    private val translateAnimation
        @Composable get() = transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = FastOutLinearInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

    val brush
        @Composable get() = Brush.linearGradient(
            colors = shimmersColorsList,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
}
