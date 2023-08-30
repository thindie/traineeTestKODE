package com.example.thindie.kodetrainee.presentation.uiunits.navigationtabssection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thindie.kodetrainee.presentation.theme.KodetraineeTheme
import com.example.thindie.kodetrainee.presentation.theme.white
import com.example.thindie.kodetrainee.presentation.uiunits.EmployeesTabs
import com.example.thindie.kodetrainee.presentation.uiunits.TabsState
import com.example.thindie.kodetrainee.presentation.uiunits.rememberTabsBarState

@Composable
fun NavigationBlock(
    searchBarState: SearchBarState,
    tabsState: TabsState,
    activateBottomSheet: () -> Unit,
) {
    Column(
        Modifier
            .background(white)
            .wrapContentHeight()
    ) {

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(white)
                .height(61.dp),
            searchBarState = searchBarState,
            activateBottomSheet = activateBottomSheet
        )
        EmployeesTabs(tabsState = tabsState)
    }

}

@Composable
@Preview
fun previewNavigationBlock() {
    KodetraineeTheme() {
        NavigationBlock(
            searchBarState = rememberSearchBarState(
                "", false, isSearchActive = false
            ),
            tabsState = rememberTabsBarState(
                ""
            )
        ) {}
    }
}

