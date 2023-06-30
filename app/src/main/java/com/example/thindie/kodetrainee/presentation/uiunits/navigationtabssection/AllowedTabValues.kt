package com.example.thindie.kodetrainee.presentation.uiunits.navigationtabssection

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.thindie.kodetrainee.R
import com.example.thindie.kodetrainee.presentation.uiunits.TabUnit

val tabValues = listOf(
    R.string.tab_tag_all to "show_all",
    R.string.tab_tag_android to "android",
    R.string.tab_tag_ios to "ios",
    R.string.tab_tag_design to "design",
    R.string.tab_tag_management to "management",
    R.string.tab_tag_qa to "qa",
    R.string.tab_tag_back_office to "back_office",
    R.string.tab_tag_frontend to "frontend",
    R.string.tab_tag_hr to "hr",
    R.string.tab_tag_pr to "pr",
    R.string.tab_tag_backend to "backend",
    R.string.tab_tag_support to "support",
    R.string.tab_tag_analytics to "analytics"
)


fun Pair<Int, String>.toTab(): TabUnit {
    return object : TabUnit {

        private val tag: String = second
        @StringRes
        private val res: Int = first


        override fun getTag(context: Context): String {
            return context.getString(res)
        }

        @Composable
        override fun getTagComposed(): String {
            return stringResource(id = res)
        }


        override fun getIdentify(): String {
            return tag
        }
    }
}