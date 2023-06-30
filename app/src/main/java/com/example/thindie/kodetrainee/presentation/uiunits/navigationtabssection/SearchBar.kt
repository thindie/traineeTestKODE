package com.example.thindie.kodetrainee.presentation.uiunits.navigationtabssection

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.thindie.kodetrainee.R
import com.example.thindie.kodetrainee.presentation.theme.KodeTraineeTypography
import com.example.thindie.kodetrainee.presentation.theme.backGroundColor
import com.example.thindie.kodetrainee.presentation.theme.lightContentActivePrimary
import com.example.thindie.kodetrainee.presentation.theme.lightContentDefaultSecondary
import com.example.thindie.kodetrainee.presentation.theme.lightTextPrimary
import com.example.thindie.kodetrainee.presentation.theme.white
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchBarState: SearchBarState,
    activateBottomSheet: () -> Unit,
) {


    with(searchBarState) {
        if (requestClearFocus.value && textFieldState.value.isBlank()) {
            focusManager.clearFocus()
            keyboardController?.hide()
            notifyFocusCleared()
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .onFocusEvent { focusState ->
                    searchBarState.onFocusState(focusState)
                }
                .padding(start = 16.dp, end = 16.dp)
                .background(white)
                .fillMaxHeight()
                .fillMaxWidth(searchBarState.currentSize.value),
            shape = RoundedCornerShape(size = 24.dp),
            value = searchBarState.textFieldState.value,
            onValueChange = searchBarState::onValueChange,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_search),
                    contentDescription = ""
                )
            },
            textStyle = KodeTraineeTypography.titleLarge,
            trailingIcon = {
                if (searchBarState.currentSize.value == 1f) {
                    IconButton(
                        onClick = activateBottomSheet,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_list_alt),
                            contentDescription = "",
                            tint = if (searchBarState.isBirthDayFilter.value) lightContentActivePrimary
                            else lightContentDefaultSecondary
                        )
                    }
                }
            },
            label = {
                if (!searchBarState.isActivated.value) {
                    Text(
                        text = stringResource(id = R.string.search_bar_hint_message),
                    )
                }
            },
            singleLine = true,
            colors = searchBarState.textFieldColorsState
        )
        if (searchBarState.isActivated.value) {
            searchBarState.onMutateSearchBarWeight()
            if (searchBarState.activateAdditionButton.value) {

                Text(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 11.dp, end = 12.dp)
                        .clickable {
                            searchBarState.onDismissSearch()
                        },
                    text = stringResource(id = R.string.button_text_cancel),
                    style = KodeTraineeTypography.titleMedium
                )
            }

        }
    }
}


@Composable
fun rememberSearchBarState(
    currentText: String,
    isTrailingIconColored: Boolean,
    isSearchActive: Boolean,
    scope: CoroutineScope = rememberCoroutineScope(),
): SearchBarState {
    return remember() {
        SearchBarState(scope, currentText, isSearchActive,isTrailingIconColored)
    }
}


@Stable
class SearchBarState(
    private val scope: CoroutineScope,
    currentText: String,
    isSearchActive: Boolean,
    isTrailingIconColored: Boolean
) {

    private val _textField: MutableState<String> = mutableStateOf(currentText)
    val textFieldState: State<String>
        get() = _textField

    private val _isBirthDayFilter = mutableStateOf(isTrailingIconColored)
    val isBirthDayFilter
        get() = _isBirthDayFilter

    private val _isSearchBarActivated = mutableStateOf(isSearchActive)
    val isActivated: State<Boolean>
        @Composable get() = _isSearchBarActivated


    private val _requestClearFocus = mutableStateOf(false)
    val requestClearFocus: State<Boolean>
        get() = _requestClearFocus

    private val _isTrailingButtonVisible = mutableStateOf(_isSearchBarActivated.value)
    val activateAdditionButton: State<Boolean>
        get() = _isTrailingButtonVisible



    val focusManager
        @Composable get() = LocalFocusManager.current

    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController
        @Composable get() = LocalSoftwareKeyboardController.current

    val currentSize
        @Composable get() = animateFloatAsState(
            targetValue = if (isActivated.value) CROP_SIZE else {
                _isTrailingButtonVisible.value = false;
                FULL_SIZE
            },
            animationSpec = tween(
                easing = LinearOutSlowInEasing, durationMillis = ANIMATE_TIME.toInt()
            ),
            visibilityThreshold = 0.0f,
        )

    val textFieldColorsState
        @Composable get() = OutlinedTextFieldDefaults.colors(
            disabledTextColor = lightContentDefaultSecondary,
            cursorColor = lightContentActivePrimary,
            errorCursorColor = MaterialTheme.colorScheme.onError,
            focusedLeadingIconColor = lightTextPrimary,
            unfocusedLeadingIconColor = lightContentDefaultSecondary,
            disabledLeadingIconColor = lightContentDefaultSecondary,
            errorLeadingIconColor = MaterialTheme.colorScheme.onError,
            focusedTextColor = lightTextPrimary,
            unfocusedTextColor = lightContentDefaultSecondary,
            errorTextColor = MaterialTheme.colorScheme.onError,
            focusedContainerColor = backGroundColor,
            unfocusedContainerColor = backGroundColor,
            disabledContainerColor = backGroundColor,
            errorContainerColor = MaterialTheme.colorScheme.onError,
            focusedTrailingIconColor = lightTextPrimary,
            unfocusedTrailingIconColor = lightContentDefaultSecondary,
            disabledTrailingIconColor = lightContentDefaultSecondary,
            errorTrailingIconColor = MaterialTheme.colorScheme.onError,
            focusedLabelColor = lightContentDefaultSecondary,
            unfocusedLabelColor = lightContentDefaultSecondary,
            disabledLabelColor = lightContentDefaultSecondary,
            errorLabelColor = MaterialTheme.colorScheme.onError,
            focusedPlaceholderColor = lightContentDefaultSecondary,
            unfocusedPlaceholderColor = lightContentDefaultSecondary,
            disabledPlaceholderColor = lightContentDefaultSecondary,
            errorPlaceholderColor = lightContentDefaultSecondary,
            focusedBorderColor = white,
            unfocusedBorderColor = white,
            disabledBorderColor = white,
            errorBorderColor = white,
        )

    fun onDismissSearch() {
        _requestClearFocus.value = true
        _isTrailingButtonVisible.value = false
        _textField.value = DEFAULT_VALUE
    }

    fun onMutateSearchBarWeight() {
        scope.launch {
            delay(ANIMATE_TIME)
            _isTrailingButtonVisible.value = true
        }
    }

    fun switchSpecialStateOn() {
        _isBirthDayFilter.value = true
    }

    fun switchSpecialStateOff() {
        _isBirthDayFilter.value = false
    }


    fun onValueChange(value: String) {
        _textField.value = value
    }

    fun onFocusState(state: FocusState) {
        if (_textField.value.isNotBlank()){
            _isSearchBarActivated.value = true
        }
        else {
            _isSearchBarActivated.value = state.isFocused
        }
    }

    fun notifyFocusCleared() {
        _requestClearFocus.value = false
    }

    companion object {
        private const val ANIMATE_TIME = 700L
        private const val FULL_SIZE = 1f
        private const val CROP_SIZE = 0.7f
        private const val DEFAULT_VALUE = ""
    }
}