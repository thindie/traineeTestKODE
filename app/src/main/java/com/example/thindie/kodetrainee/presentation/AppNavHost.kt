package com.example.thindie.kodetrainee.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.example.thindie.kodetrainee.presentation.EmployeeAppState
import com.example.thindie.kodetrainee.presentation.screens.allemployees.mainScreen
import com.example.thindie.kodetrainee.presentation.screens.concreteemployee.concreteScreen


@Composable

fun AppNavHost(
    appState: EmployeeAppState,
    onAction: (String) -> Unit,
) {
    NavHost(
        navController = appState.navController,
        startDestination = appState.startDestination
    ) {
        mainScreen(
            onError = appState::error,
            onClickConcrete = appState::concrete
        )
        concreteScreen(
            onClickBack = appState::main,
            onError = appState::error,
            onAction = onAction
        )
        errorScreen(onClickRetry = appState::retry)

    }

}

