package com.example.thindie.kodetrainee.presentation.uiunits

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thindie.kodetrainee.presentation.theme.KodeTraineeTypography
import com.example.thindie.kodetrainee.presentation.theme.KodetraineeTheme
import com.example.thindie.kodetrainee.presentation.uiunits.navigationtabssection.tabValues
import com.example.thindie.kodetrainee.presentation.uiunits.navigationtabssection.toTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun EmployeesTabs(modifier: Modifier = Modifier, tabsState: TabsState) {

    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = tabsState.index.value,

        ) {
        tabsState.list.forEachIndexed { index, tabUnit ->
            val selected = mutableStateOf(tabsState.index.value == index)
            Tab(
                modifier = Modifier
                    .height(55.dp)
                    .padding(bottom = 8.dp),
                text = {
                    if (selected.value) {
                        tabsState.selectedStyle.invoke(tabUnit)
                    } else {
                        tabsState.unSelectedStyle.invoke(tabUnit)
                    }
                },
                selected = selected.value,
                onClick = { tabsState.onClickTab(tabUnit.getIdentify()) },
                icon = {}
            )
        }
    }
}

@Composable
fun rememberTabsBarState(
    currentTab: String,
): TabsState {

    return remember(currentTab) {
        TabsState(currentTab)
    }

}

@Stable
class TabsState(
    currentTab: String,
) {
    private val _currentTabTag = mutableStateOf(currentTab)
    val onClick: State<String>
        get() = _currentTabTag

    val list = tabValues.map {
        it.toTab()
    }


    val index = mutableStateOf(
        list
            .indexOf(list
                .find { it.getIdentify() == _currentTabTag.value })
    )


    fun onClickTab(tab: String) {
        _currentTabTag.value = tab
    }


    val selectedStyle: @Composable (TabUnit) -> Unit = { tabUnit ->
        Text(
            text = tabUnit.getTagComposed(),
            style = KodeTraineeTypography.titleLarge
        )
    }

    val unSelectedStyle: @Composable (TabUnit) -> Unit = { tabUnit ->
        Text(
            text = tabUnit.getTagComposed(),
            style = KodeTraineeTypography.labelLarge
        )
    }
}


@Preview
@Composable
fun previewEmployeesTabs() {
    KodetraineeTheme() {
        EmployeesTabs(tabsState = rememberTabsBarState(""))
    }
}
