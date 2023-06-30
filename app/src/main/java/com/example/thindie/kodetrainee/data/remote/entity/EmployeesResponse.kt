package com.example.thindie.kodetrainee.data.remote.entity

import com.google.gson.annotations.SerializedName

data class EmployeesResponse(
    @SerializedName("items")
    val employeeList: List<EmployeeDto>
)