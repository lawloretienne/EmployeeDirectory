package com.example.employeedirectory

import com.example.employeedirectory.data.network.EmployeesService
import com.example.employeedirectory.data.repositories.employee.EmployeeRemoteDataSource
import com.example.employeedirectory.fakers.EmployeeFaker
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EmployeeRemoteDataSourceTest : BaseTest() {

    @RelaxedMockK
    lateinit var employeesService: EmployeesService

    private lateinit var employeeRemoteDataSource: EmployeeRemoteDataSource

    @BeforeEach
    override fun setUp() {
        super.setUp()
        employeeRemoteDataSource = EmployeeRemoteDataSource(employeesService)
    }

    @Test
    fun `getEmployees should return Employees`() = runBlocking {
        val employees = EmployeeFaker.list()

        coEvery { employeesService.getEmployees().employees }
            .answers { employees }

        val result = employeeRemoteDataSource.getEmployees()
        assert(result == employees)
    }
}