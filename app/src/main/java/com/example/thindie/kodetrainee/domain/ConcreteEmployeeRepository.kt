package com.example.thindie.kodetrainee.domain

interface ConcreteEmployeeRepository {
    suspend fun getEmployee(): Result<Employee>
}