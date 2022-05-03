package com.example.employeedirectory.data.repositories.employee

import com.example.employeedirectory.data.network.EmployeesService
import com.example.employeedirectory.data.network.response.EmployeeResponse
import javax.inject.Inject

class EmployeeRemoteDataSource @Inject constructor(
    private val employeesService: EmployeesService
) {

    suspend fun getEmployees() : List<EmployeeResponse> {
        return employeesService.getEmployees().employees
    }

    suspend fun getEmployeesMalformed() : List<EmployeeResponse> {
        return employeesService.getEmployeesMalformed().employees
    }

    suspend fun getEmployeesEmpty() : List<EmployeeResponse> {
        return employeesService.getEmployeesEmpty().employees
    }
}