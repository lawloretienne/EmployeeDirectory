package com.example.employeedirectory.data.network.response

import android.telephony.PhoneNumberUtils
import com.example.employeedirectory.ui.models.DomainMapper
import com.example.employeedirectory.ui.models.Employee
import com.google.gson.annotations.SerializedName

data class EmployeesResponse(
    val employees: List<EmployeeResponse>
)

data class EmployeeResponse(
    val uuid: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("email_address")
    val emailAddress: String,
    val biography: String,
    @SerializedName("photo_url_small")
    val photoUrlSmall: String,
    @SerializedName("photo_url_large")
    val photoUrlLarge: String,
    val team: String,
    @SerializedName("employee_type")
    val employeeType: EmployeeType
) : DomainMapper<Employee> {
    override fun toDomainModel(): Employee {
        return Employee(
            uuid = uuid,
            fullName = fullName,
            phoneNumber = formatNumber(phoneNumber),
            emailAddress = emailAddress,
            biography = biography,
            photoUrlSmall = photoUrlSmall,
            photoUrlLarge = photoUrlLarge,
            team = team,
            employeeType = employeeType.value
        )
    }
}

enum class EmployeeType(val value: String) {
    FULL_TIME("Full Time"),
    PART_TIME("Part Time"),
    CONTRACTOR("Contractor")
}

fun formatNumber(number: String): String {
    return if(number.length == 10) PhoneNumberUtils.formatNumber(number, "US")
        else number
}