package com.example.thindie.kodetrainee.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.thindie.kodetrainee.R
import com.example.thindie.kodetrainee.presentation.navigation.errorRoute
import com.example.thindie.kodetrainee.presentation.theme.KodeTraineeTypography
import com.example.thindie.kodetrainee.presentation.theme.KodetraineeTheme


fun NavGraphBuilder.errorScreen(onClickRetry: () -> Unit) {
    composable(errorRoute.route) {
        CriticalScreenError (onClickRetry = onClickRetry)
    }
}


@Composable
fun CriticalScreenError(onClickRetry: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .size(56.dp),
                contentScale = ContentScale.Fit,
                painter = painterResource(id = R.drawable.critical_error_ufo_image),
                contentDescription = "",
            )
            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                text = stringResource(R.string.critical_message_mind_broke),
                style = KodeTraineeTypography.titleSmall
            )

            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                text = stringResource(R.string.critical_message_repair_soon),
                style = KodeTraineeTypography.bodyLarge
            )

            Text(
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .clickable { onClickRetry() },
                text = stringResource(R.string.critical_attempt_retry),
                style = KodeTraineeTypography.titleMedium
            )
        }
    }
}

@Composable
@Preview
fun previewCriticalError() {
    KodetraineeTheme() {
        CriticalScreenError({})
    }
}