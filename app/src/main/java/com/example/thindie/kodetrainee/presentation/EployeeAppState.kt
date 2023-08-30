package com.example.thindie.kodetrainee.presentation


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.thindie.kodetrainee.presentation.navigation.concreteRoute
import com.example.thindie.kodetrainee.presentation.navigation.errorRoute
import com.example.thindie.kodetrainee.presentation.navigation.mainRoute
import dagger.assisted.AssistedInject


@Composable
fun rememberEmployeeAppState(
    navHostController: NavHostController = rememberNavController(),
): EmployeeAppState {
    return remember(
        navHostController,
    ) {
        EmployeeAppState(
            navController = navHostController,
        )
    }
}


@Stable
class EmployeeAppState(
    val navController: NavHostController,
) {

    val startDestination: String
        @Composable get() = mainRoute.route

    private fun navigate(route: String) {
        navController.navigateSingleTopTo(route)
    }

    fun error() {
        navigate(errorRoute.route)
    }

    fun main() {
        navigate(mainRoute.route)
    }

    fun concrete() {
        navigate(concreteRoute.route)
    }

    fun retry() {
        main()
    }

    private fun NavHostController.navigateSingleTopTo(route: String) =
        this.navigate(route) {
            popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
}