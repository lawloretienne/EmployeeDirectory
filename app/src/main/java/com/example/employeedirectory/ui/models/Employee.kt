package com.example.employeedirectory.ui.models

data class Employee(
    val uuid: String,
    val fullName: String,
    val phoneNumber: String,
    val emailAddress: String,
    val biography: String,
    val photoUrlSmall: String,
    val photoUrlLarge: String,
    val team: String,
    val employeeType: String
)