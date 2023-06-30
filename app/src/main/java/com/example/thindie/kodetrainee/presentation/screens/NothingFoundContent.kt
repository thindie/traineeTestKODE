package com.example.thindie.kodetrainee.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thindie.kodetrainee.R
import com.example.thindie.kodetrainee.presentation.theme.KodeTraineeTypography
import com.example.thindie.kodetrainee.presentation.theme.KodetraineeTheme

@Composable
fun NothingFoundContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(56.dp)
                .padding(bottom = 8.dp),
            painter = painterResource(id = R.drawable.stub_magnifying_glass),
            contentScale = ContentScale.Fit,
            contentDescription = ""
        )
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = stringResource(R.string.message_nothing_found),
            style = KodeTraineeTypography.titleSmall
        )
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = stringResource(R.string.message_correct_request),
            style = KodeTraineeTypography.labelLarge
        )
    }
}

@Composable
@Preview
fun previewNothingFound() {
    KodetraineeTheme {
        NothingFoundContent()
    }
}