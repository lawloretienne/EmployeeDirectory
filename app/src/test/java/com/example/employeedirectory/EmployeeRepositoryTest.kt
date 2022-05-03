package com.example.employeedirectory

import com.example.employeedirectory.data.repositories.employee.EmployeeRemoteDataSource
import com.example.employeedirectory.data.repositories.employee.EmployeeRepository
import com.example.employeedirectory.fakers.EmployeeFaker
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EmployeeRepositoryTest : BaseTest() {

    @MockK
    lateinit var employeeRemoteDataSource: EmployeeRemoteDataSource

    private lateinit var employeeRepository: EmployeeRepository

    @BeforeEach
    override fun setUp() {
        super.setUp()
        employeeRepository = EmployeeRepository(employeeRemoteDataSource)
    }

    @Test
    fun `getEmployees should return Employees`() = runBlocking {
        val employees = EmployeeFaker.list()

        coEvery { employeeRemoteDataSource.getEmployees() }
            .answers { employees}

        val result = employeeRepository.getEmployees()
        assert(result == employees)
    }
}