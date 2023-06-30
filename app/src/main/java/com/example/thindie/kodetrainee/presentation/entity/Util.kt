package com.example.thindie.kodetrainee.presentation.entity

val superDumbSolution by lazy {
    mapOf(
        "1" to " год",
        "2" to " года",
        "3" to " года",
        "4" to " года",
        "5" to " лет",
        "6" to " лет",
        "7" to " лет",
        "8" to " лет",
        "9" to " лет",
        "0" to " лет"
    )
}

val teenAgeSituation by lazy {
    mapOf(
        "11" to " лет",
        "12" to " лет",
        "13" to " лет",
        "14" to " лет",
    )
}

val monthStringProvider by lazy {
    mapOf(
        1 to " января ",
        2 to " февраля ",
        3 to " марта ",
        4 to " апреля ",
        5 to " мая ",
        6 to " июня ",
        7 to " июля ",
        8 to " августа ",
        9 to " сентября ",
        10 to " октября ",
        11 to " ноября ",
        12 to " декабря ",
    )
}

fun Int.toStringMonth(): String {
    return monthStringProvider.getValue(this)
}