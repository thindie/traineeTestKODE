package com.example.thindie.kodetrainee.domain

interface Repository {
    suspend fun getEmployees(): Result<List<Employee>>
    fun setEmployee(employee: Employee)
}