package com.example.employeedirectory.fakers

import com.example.employeedirectory.data.network.response.EmployeeResponse
import com.example.employeedirectory.data.network.response.EmployeeType
import com.example.employeedirectory.fakers.BaseFaker.Companion.Fake.faker
import com.example.employeedirectory.ui.models.Employee

object EmployeeFaker {

    fun basic() = EmployeeResponse(
        faker.idNumber().valid(),
        faker.name().fullName(),
        faker.phoneNumber().phoneNumber(),
        faker.internet().emailAddress(),
        faker.lorem().word(),
        faker.internet().url(),
        faker.internet().url(),
        faker.lorem().word(),
        EmployeeType.FULL_TIME
    )

    fun list() = BaseFaker.list { basic() }
}