package com.example.employeedirectory.ui.employees

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeedirectory.data.repositories.employee.EmployeeRepository
import com.example.employeedirectory.ui.models.Employee
import com.example.employeedirectory.ui.models.toDomainModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class EmployeesViewModel @Inject constructor(
    val employeeRepository: EmployeeRepository) : ViewModel() {

    val viewState: LiveData<ViewState>
        get() = _viewState
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    sealed class ViewState {
        object Loading : ViewState()
        object Error : ViewState()
        object Empty: ViewState()
        class Success(val employees: List<Employee>): ViewState()
    }

    fun getEmployees() {
        _viewState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                try {
                    var employees = employeeRepository.getEmployees().sortedBy { it.fullName }.toDomainModels()
                    _viewState.value =
                        if(employees.isNotEmpty()) ViewState.Success(employees)
                        else ViewState.Empty
                } catch (e: Exception) {
                    Timber.e(e)
                    _viewState.value = ViewState.Error
                }
            }
        }
    }

    // This invokes the malformed employees endpoint
    fun getEmployeesMalformed() {
        _viewState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                try {
                    var employees = employeeRepository.getEmployeesMalformed().sortedBy { it.fullName }.toDomainModels()
                    _viewState.value =
                        if(employees.isNotEmpty()) ViewState.Success(employees)
                        else ViewState.Empty
                } catch (e: Exception) {
                    Timber.e(e)
                    _viewState.value = ViewState.Error
                }
            }
        }
    }

    // This invokes the empty employees endpoint
    fun getEmployeesEmpty() {
        _viewState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                try {
                    var employees = employeeRepository.getEmployeesEmpty().sortedBy { it.fullName }.toDomainModels()
                    _viewState.value =
                        if(employees.isNotEmpty()) ViewState.Success(employees)
                        else ViewState.Empty

                } catch (e: Exception) {
                    Timber.e(e)
                    _viewState.value = ViewState.Error
                }
            }
        }
    }
}