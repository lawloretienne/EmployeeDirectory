package com.example.employeedirectory.data.repositories.employee

import com.example.employeedirectory.data.network.response.EmployeeResponse
import javax.inject.Inject

class EmployeeRepository @Inject constructor(
    private val employeeRemoteDataSource: EmployeeRemoteDataSource) {

    suspend fun getEmployees(): List<EmployeeResponse> {
        return employeeRemoteDataSource.getEmployees()
    }

    suspend fun getEmployeesMalformed(): List<EmployeeResponse> {
        return employeeRemoteDataSource.getEmployeesMalformed()
    }

    suspend fun getEmployeesEmpty(): List<EmployeeResponse> {
        return employeeRemoteDataSource.getEmployeesEmpty()
    }
}