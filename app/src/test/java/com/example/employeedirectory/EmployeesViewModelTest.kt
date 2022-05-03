package com.example.employeedirectory

import androidx.lifecycle.Observer
import com.example.employeedirectory.data.repositories.employee.EmployeeRepository
import com.example.employeedirectory.fakers.EmployeeFaker
import com.example.employeedirectory.ui.employees.EmployeesViewModel
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EmployeesViewModelTest : BaseTest() {

    @RelaxedMockK
    lateinit var employeeRepository: EmployeeRepository

    @RelaxedMockK
    lateinit var stateObserver: Observer<EmployeesViewModel.ViewState>

    private lateinit var employeesViewModel: EmployeesViewModel

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @BeforeEach
    override fun setUp() {
        super.setUp()
        Dispatchers.setMain(mainThreadSurrogate)
        employeesViewModel = EmployeesViewModel(employeeRepository)
    }

    @AfterEach
    override fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `getEmployees should show Success state`() = runBlocking {
        // 1. (Given) Set up conditions required for the test
        employeesViewModel.viewState.observeForever(stateObserver)
        val employees = EmployeeFaker.list()

        coEvery { employeeRepository.getEmployees() }
            .answers { employees }

        // 2. (When) Then perform one or more actions
        launch(Dispatchers.Main) {
            employeesViewModel.getEmployees()
        }

        // 3. (Then) Afterwards, verify that the state you are expecting is actually achieved
        verifySequence {
            stateObserver.onChanged(EmployeesViewModel.ViewState.Loading)
            stateObserver.onChanged(any<EmployeesViewModel.ViewState.Success>())
        }
    }

    @Test
    fun `getEmployees should show Error state when throws exception`() = runBlocking {
        // 1. (Given) Set up conditions required for the test
        employeesViewModel.viewState.observeForever(stateObserver)
        val throwable = Exception()

        coEvery { employeeRepository.getEmployees() } throws throwable

        // 2. (When) Then perform one or more actions
        employeesViewModel.getEmployees()

        // 3. (Then) Afterwards, verify that the state you are expecting is actually achieved
        verifySequence {
            stateObserver.onChanged(EmployeesViewModel.ViewState.Loading)
            stateObserver.onChanged(EmployeesViewModel.ViewState.Error)
        }
    }

    @Test
    fun `getEmployeesEmpty should show Empty state`() = runBlocking {
        // 1. (Given) Set up conditions required for the test
        employeesViewModel.viewState.observeForever(stateObserver)

        coEvery { employeeRepository.getEmployeesEmpty() }
            .answers { emptyList() }

        // 2. (When) Then perform one or more actions
        launch(Dispatchers.Main) {
            employeesViewModel.getEmployeesEmpty()
        }

        // 3. (Then) Afterwards, verify that the state you are expecting is actually achieved
        verifySequence {
            stateObserver.onChanged(EmployeesViewModel.ViewState.Loading)
            stateObserver.onChanged(EmployeesViewModel.ViewState.Empty)
        }
    }

    @Test
    fun `getEmployeesMalformed should show Error state`() = runBlocking {
        // 1. (Given) Set up conditions required for the test
        employeesViewModel.viewState.observeForever(stateObserver)

        val throwable = Exception()
        coEvery { employeeRepository.getEmployeesMalformed() } throws throwable

        // 2. (When) Then perform one or more actions
        launch(Dispatchers.Main) {
            employeesViewModel.getEmployeesMalformed()
        }

        // 3. (Then) Afterwards, verify that the state you are expecting is actually achieved
        verifySequence {
            stateObserver.onChanged(EmployeesViewModel.ViewState.Loading)
            stateObserver.onChanged(EmployeesViewModel.ViewState.Error)
        }
    }
}