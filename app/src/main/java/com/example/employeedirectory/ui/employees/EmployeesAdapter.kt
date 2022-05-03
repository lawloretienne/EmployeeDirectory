package com.example.employeedirectory.ui.employees

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.employeedirectory.databinding.EmployeeRowBinding
import com.example.employeedirectory.ui.models.Employee

class EmployeesAdapter : ListAdapter<Employee, RecyclerView.ViewHolder>(EmployeeDiffCallback()) {

    private lateinit var binding: EmployeeRowBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = EmployeeRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val viewHolder = EmployeeViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = viewHolder as EmployeeViewHolder

        val employee = getItem(position)
        holder.bind(employee)
    }
}