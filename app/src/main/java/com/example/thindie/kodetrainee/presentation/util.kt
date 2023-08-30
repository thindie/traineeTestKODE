package com.example.thindie.kodetrainee.presentation

import com.example.thindie.kodetrainee.domain.Employee
import com.example.thindie.kodetrainee.presentation.uiunits.SearchAble
import com.example.thindie.kodetrainee.presentation.entity.EmployeeAgeHub
import com.example.thindie.kodetrainee.presentation.entity.EmployeeUiModel
import javax.inject.Inject

fun Employee.toUiModel() =
    EmployeeUiModel(
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

fun String.matchCriteria(criteria: String): Boolean {
    return lowercase().contains(criteria.lowercase()) ||
            lowercase() == criteria.lowercase() ||
            criteria.lowercase().contains(this.lowercase())
}

class AlphabetCompare @Inject constructor(): java.util.Comparator<SearchAble> {
    override fun compare(p0: SearchAble?, p1: SearchAble?): Int {
        if (p0 == null) return -1
        if (p1 == null) return 1
        return p0.getMajorComponent1().compareTo(p1.getMajorComponent1())
    }
 }

class BirthDayCompare @Inject constructor() : java.util.Comparator<SearchAble> {
    override fun compare(p0: SearchAble?, p1: SearchAble?): Int {
        if (p0 == null) return -1
        if (p1 == null) return 1
        return p0.getMajorComponent<EmployeeAgeHub>().compareTo(p1.getMajorComponent())
    }

}

