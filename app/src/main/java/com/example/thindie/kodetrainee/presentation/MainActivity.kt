package com.example.thindie.kodetrainee.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.thindie.kodetrainee.presentation.screens.AppNavHost
import com.example.thindie.kodetrainee.presentation.theme.KodetraineeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KodetraineeTheme {
                AppNavHost(
                    rememberEmployeeAppState(),
                    onAction = this::callIntent
                )
            }
        }
    }

    private fun callIntent(number: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:".plus(number))
        }
        startActivity(intent)
    }
}



