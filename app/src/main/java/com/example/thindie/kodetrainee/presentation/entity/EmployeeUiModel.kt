package com.example.thindie.kodetrainee.presentation.entity

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.thindie.kodetrainee.domain.Employee
import com.example.thindie.kodetrainee.presentation.uiunits.SearchAble
import java.time.LocalDate
import java.time.Period
import java.util.*

data class EmployeeUiModel(
    val avatarUrl: String,
    val birthday: String,
    val department: String,
    val firstName: String,
    val id: String,
    val lastName: String,
    val phone: String,
    val position: String,
    val userTag: String,
    val mail: String = firstName.plus(MAIL),
    val ageHub: EmployeeAgeHub = getAgeHubDateCompared(birthday),
) : SearchAble {
    override fun getTagShadow(): String {
        return firstName
            .plus(lastName)
            .plus(userTag)
            .plus(mail)

    }

    override fun getID(): String {
        return id
    }

    override fun <T> getMajorComponent(): T {
        return ageHub as T
    }


    override fun getMajorComponent1(): String {
        return firstName
    }


    fun toEmployee(): Employee {
        return Employee(
            avatarUrl = avatarUrl,
            birthday = birthday,
            department = department,
            firstName = firstName,
            id = id,
            lastName = lastName,
            phone = phone,
            position = position,
            userTag = userTag
        )
    }


    companion object {
        private const val MAIL = "@kodemail.com"

        fun getAgeHubDateCompared(birthday: String): EmployeeAgeHub {
            require(checkBirthday(birthday))
            val dateHub = getParseIntListFromString(birthday, "-")
            return EmployeeAgeHub(
                day = dateHub[2],
                month = dateHub[1],
                year = dateHub[0],
                age = calculateAge(
                    dateHub
                )
            )
        }


        fun calculateAge(dateHub: List<Int>): String {

            val y: Int = dateHub[0]
            val m: Int = dateHub[1]
            val d: Int = dateHub[2]

            val beatifyAge =
                Period.between(
                    LocalDate.of(y, m, d),
                    LocalDate.now()
                ).years.toString()


            val suffix = detectAgeSufix(beatifyAge)

            return beatifyAge.plus(suffix)
        }


        fun formatBirthday(ageHub: EmployeeAgeHub): String {
            return ageHub.day
                .toString()
                .plus(ageHub.month.toStringMonth())
                .plus(ageHub.year)
        }

        fun formatBirthdayWithoutAge(ageHub: EmployeeAgeHub): String {
            return ageHub.day
                .toString()
                .plus(ageHub.month.toStringMonth().substring(0, 4))
        }



        private fun getParseIntListFromString(string: String, delim: String): List<Int> {
            return string.split(delim)
                .map { it.toInt() }
        }

        private fun detectAgeSufix(beatifyAge: String): String {
            return if (teenAgeSituation.containsKey(beatifyAge)) {
                teenAgeSituation.getValue(beatifyAge)
            } else {
                superDumbSolution.getValue(beatifyAge.last().toString()) ?: ""
            }
        }

        private fun checkBirthday(birthday: String) =
            birthday.matches("\\d{4}-\\d{2}-\\d{2}".toRegex())
    }

}





