package com.example.thindie.kodetrainee.presentation.uiunits

import android.content.Context
import androidx.compose.runtime.Composable

interface TabUnit {
    fun getTag(context: Context): String
    fun getIdentify(): String
    @Composable
    fun getTagComposed(): String

}