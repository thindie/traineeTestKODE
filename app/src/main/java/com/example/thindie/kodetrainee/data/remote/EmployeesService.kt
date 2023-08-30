package com.example.thindie.kodetrainee.data.remote;

import com.example.thindie.kodetrainee.data.remote.entity.EmployeesResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface EmployeesService {
    @GET("users")
    @Headers("Accept: application/json")
    suspend fun getEmployees(): EmployeesResponse
}
