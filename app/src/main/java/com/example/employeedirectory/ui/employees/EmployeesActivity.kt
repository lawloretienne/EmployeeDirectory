package com.example.employeedirectory.ui.employees

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.employeedirectory.databinding.ActivityEmployeesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeesActivity : AppCompatActivity() {

    private val employeesViewModel: EmployeesViewModel by viewModels()
    private val employeesAdapter = EmployeesAdapter()

    private lateinit var binding: ActivityEmployeesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmployeesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.contentLayout.swipeContainer.setOnRefreshListener { employeesViewModel.getEmployees() }

        binding.contentLayout.recyclerView.apply {
            adapter = employeesAdapter
            addItemDecoration(EmployeeItemDecoration())
        }

        binding.errorLayout.reloadButton.setOnClickListener { employeesViewModel.getEmployees() }

        employeesViewModel.getEmployees()

        employeesViewModel.viewState.observe(this, Observer { handleState(it) })
    }

    private fun handleState(state: EmployeesViewModel.ViewState) = with( binding) {
        loadingLayout.progressBar.isVisible = state is EmployeesViewModel.ViewState.Loading
        errorLayout.errorLinearLayout.isVisible = state is EmployeesViewModel.ViewState.Error
        contentLayout.recyclerView.isVisible = state is EmployeesViewModel.ViewState.Success
        emptyLayout.root.isVisible = state is EmployeesViewModel.ViewState.Empty

        if(state is EmployeesViewModel.ViewState.Success){
            employeesAdapter.submitList(state.employees)
        }

        if(state !is EmployeesViewModel.ViewState.Loading)
            contentLayout.swipeContainer.isRefreshing = false
    }
}