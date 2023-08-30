package com.example.thindie.kodetrainee.presentation.uiunits.shimmeringskeleton

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thindie.kodetrainee.presentation.theme.KodetraineeTheme


@Composable
fun rememberShimmerStateScreenDecoration(
    isWideScreenSize: Boolean = false,
    transition: InfiniteTransition = rememberInfiniteTransition(),
): ShimmerState {
    return remember(isWideScreenSize, transition) {
        ShimmerState(
            isWideScreenSize,
            transition
        )
    }
}


@Composable
fun ShimmeringList(brush: Brush) {
    LazyColumn(modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)) {
        items(9) {
            Spacer(modifier = Modifier.padding(top = 12.dp))
            ForListScreensElement(brush = brush)
        }
    }
}


@Composable
private fun ForListScreensElement(brush: Brush, modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .background(color = MaterialTheme.colorScheme.surface)
            .height(84.dp)
    ) {
        Column(
            modifier
                .weight(0.2f)
                .padding(top = 6.dp, bottom = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            RoundShimmer(brush = brush)
        }
        Column(
            modifier
                .padding(start = 16.dp)
                .weight(0.8f),

            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier
                    .padding(top = 25.dp)
            ) {
                LineShimmer(
                    brush, modifier = modifier
                        .width(144.dp)
                        .height(16.dp)
                )

            }
            Row(modifier.padding(top = 6.dp)) {
                LineShimmer(
                    brush = brush,
                    modifier = modifier
                        .height(12.dp)
                        .width(80.dp)
                )
            }
        }
    }
}


@Composable
private fun RoundShimmer(brush: Brush, modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(brush)
    )
}

@Composable
private fun LineShimmer(brush: Brush, modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .fillMaxWidth(0.6f)
            .height(15.dp)
            .clip(ShapeDefaults.ExtraLarge)
            .background(brush)
    )
}

@Preview
@Composable
fun previewShimmer() {
    val state = rememberShimmerStateScreenDecoration()
    KodetraineeTheme() {
        ShimmeringList(brush = state.brush)
    }
}
