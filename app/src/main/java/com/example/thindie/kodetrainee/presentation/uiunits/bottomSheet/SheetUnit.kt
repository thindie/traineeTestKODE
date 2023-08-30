package com.example.thindie.kodetrainee.presentation.uiunits.bottomSheet

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

interface SheetUnit {
    @get:DrawableRes
    val image: State<Int>
    fun getTag(context: Context): String
    @Composable
    fun getTagComposed(): String
    fun select()
    fun deSelect()
    fun getType(): SelectedType
    fun isSelected(): Boolean
}