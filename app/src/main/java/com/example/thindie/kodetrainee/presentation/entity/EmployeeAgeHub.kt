package com.example.thindie.kodetrainee.presentation.entity


import java.time.LocalDate

data class EmployeeAgeHub(
    val day: Int,
    val month: Int,
    val year: Int,
    val age: String = EmployeeUiModel.calculateAge(listOf(year, month, day)),
) : Comparable<EmployeeAgeHub> {

    val formattedBirthDay
        get() = EmployeeUiModel.formatBirthday(this)

    fun isAtNextYear(): Boolean {
        return getBirthdayYearLenghtInteger() < LocalDate.now().dayOfYear
    }


    override fun compareTo(other: EmployeeAgeHub): Int {
        return getBirthdayYearLenghtInteger().compareTo(other.getBirthdayYearLenghtInteger())
    }


    private fun getBirthdayYearLenghtInteger(): Int {
        val date = LocalDate.of(year, month, day)
        return date.dayOfYear
    }
}