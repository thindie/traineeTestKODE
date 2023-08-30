package com.example.thindie.kodetrainee.presentation.uiunits.bottomSheet

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


private val byBirthDay: BottomSheetUnit = BottomSheetUnit(
    tagMessage = R.string.message_bottom_sheet_birhtday, unitType = SelectedType.BIRTHDAY
)

private val byAlphabet: BottomSheetUnit = BottomSheetUnit(
    tagMessage = R.string.message_bottom_sheet_alphabet, unitType = SelectedType.ALPHABET
)

val bottomSheetOptions = listOf(byAlphabet, byBirthDay)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeBottomSheet(state: EmployeeBottomSheetState) {

    if (state.sheetState.isVisible) {
        ModalBottomSheet(
            modifier = Modifier
                .height(252.dp)
                .padding(start = 8.dp, end = 8.dp),
            sheetState = state.sheetState,
            onDismissRequest = {
                state.scope.launch {
                    state.sheetState.hide()
                }
            },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.bottom_sheet_tag_sort),
                    style = KodeTraineeTypography.headlineLarge
                )
            }
            LazyColumn(modifier = Modifier.padding(bottom = 34.dp)) {
                itemsIndexed(items = state.options) { index, sheetUnit ->
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .height(60.dp),
                        horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(painter = painterResource(id = sheetUnit.image.value),
                            contentDescription = sheetUnit.getTagComposed(),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { state.onSelect(index) })
                        Text(
                            modifier = Modifier.padding(start = 12.dp),
                            text = sheetUnit.getTagComposed(),
                            style = KodeTraineeTypography.titleLarge
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberEmployeeBottomSheetState(
    isAlphabetFiltering: Boolean,
): EmployeeBottomSheetState {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    return remember(sheetState, scope) {
        EmployeeBottomSheetState(isAlphabetFiltering, sheetState, scope)
    }
}

@Stable
@OptIn(ExperimentalMaterial3Api::class)
class EmployeeBottomSheetState constructor(
    isAlphabetFiltering: Boolean,
    private val state: SheetState,
    val scope: CoroutineScope,
    val options: List<BottomSheetUnit> = bottomSheetOptions,
) {


    private val _selected =
        MutableStateFlow(if (isAlphabetFiltering) SelectedType.ALPHABET else SelectedType.BIRTHDAY)
    val selected: StateFlow<SelectedType>
        get() = _selected.asStateFlow()

    init {
        options.forEach { sheetUnit ->
            if (sheetUnit.unitType == _selected.value) sheetUnit.select()
            else sheetUnit.deSelect()
        }
    }

    val sheetState
        get() = state

    fun onSelect(index: Int) {
        if (isNotClickedSameSelection(index)) {
            rememberSelectedType(index)
            resetSelection()
            options[index].select()
            _selected.value = (options[index].getType())
            hideList()
        }
    }

    private fun rememberSelectedType(index: Int) {
        _selected.value = options[index].unitType
    }

    private fun resetSelection() {
        options.forEach {
            it.deSelect()
        }
    }

    private fun isNotClickedSameSelection(index: Int): Boolean {
        return options[index].unitType != _selected.value
    }

    fun showList() {
        scope.launch {
            sheetState.show()
        }
    }

    private fun hideList() {
        scope.launch {
            sheetState.hide()
        }
    }

}

@Preview
@Composable
fun previewBottomSheet() {

    KodetraineeTheme {
        EmployeeBottomSheet(rememberEmployeeBottomSheetState(false))
    }
}


data class BottomSheetUnit(
    @StringRes val tagMessage: Int,
    val unitType: SelectedType,
    @DrawableRes val selectedR: Int = R.drawable.icon_radio_selected_24_24,
    @DrawableRes val deselectedR: Int = R.drawable.icon_radio_unselected_24_24,
) : SheetUnit {
    private val _image = mutableStateOf(deselectedR)

    @Composable
    override fun getTagComposed(): String {
        return stringResource(tagMessage)
    }

    override fun getTag(context: Context): String {
        return context.getString(tagMessage)
    }

    override fun select() {
        _image.value = selectedR
    }

    override fun deSelect() {
        _image.value = deselectedR
    }

    override val image: State<Int>
        get() = _image

    override fun getType(): SelectedType {
        return unitType
    }

    override fun isSelected(): Boolean {
        return _image.value == selectedR
    }

}


