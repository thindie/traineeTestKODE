package com.example.thindie.kodetrainee.presentation.navigation

private const val ERROR = "error"
private const val CONCRETE_SCREEN = "concrete"
private const val MAIN_SCREEN = "main"

data class Destination(
    val route: String
)

val errorRoute = Destination(ERROR)
val concreteRoute = Destination(CONCRETE_SCREEN)
val mainRoute = Destination(MAIN_SCREEN)

val destinations = listOf(mainRoute, concreteRoute, errorRoute)