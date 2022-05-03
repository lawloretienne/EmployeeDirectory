package com.example.employeedirectory.ui.employees

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.employeedirectory.R
import com.example.employeedirectory.databinding.EmployeeRowBinding
import com.example.employeedirectory.ui.models.Employee

class EmployeeViewHolder(binding: EmployeeRowBinding) : RecyclerView.ViewHolder(binding.root) {

    private val imageView = binding.imageView
    private val fullNameTextView = binding.fullNameTextView
    private val teamTextView = binding.teamTextView
    private val emailAddressTextView = binding.emailAddressTextView
    private val phoneNumberTextView = binding.phoneNumberTextView
    private val employeeTypeTextView = binding.employeeTypeTextView
    private val biographyTypeTextView = binding.biographyTypeTextView

    fun bind(employee: Employee) {

        imageView.load(employee.photoUrlSmall) {
            transformations(CircleCropTransformation())
            placeholder(R.drawable.photo_placeholder)
            error(R.drawable.photo_error)
        }

        fullNameTextView.text = employee.fullName
        teamTextView.text = employee.team
        employeeTypeTextView.text = employee.employeeType
        emailAddressTextView.text = employee.emailAddress
        phoneNumberTextView.text = employee.phoneNumber
        fullNameTextView.text = employee.fullName
        biographyTypeTextView.text = employee.biography
    }
}