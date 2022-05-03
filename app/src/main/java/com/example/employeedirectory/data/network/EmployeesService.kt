package com.example.employeedirectory.data.network

import com.example.employeedirectory.data.network.response.EmployeesResponse
import retrofit2.http.GET

interface EmployeesService {

    @GET("employees.json")
    suspend fun getEmployees(): EmployeesResponse

    @GET("employees_malformed.json")
    suspend fun getEmployeesMalformed(): EmployeesResponse

    @GET("employees_empty.json")
    suspend fun getEmployeesEmpty(): EmployeesResponse

}