package com.example.thindie.kodetrainee.data

import com.example.thindie.kodetrainee.data.remote.entity.EmployeeDto
import com.example.thindie.kodetrainee.domain.Employee
//due main api broken
private const val IMAGE_URL = "https://loremflickr.com/320/240/human?lock="
fun EmployeeDto.toEmployee(counter: Int) =
    Employee(
        avatarUrl = IMAGE_URL.plus(counter.toString()),
        birthday = birthday,
        department = department,
        firstName = firstName,
        id = id,
        lastName = lastName,
        phone = phone,
        position = position,
        userTag = userTag
    )


