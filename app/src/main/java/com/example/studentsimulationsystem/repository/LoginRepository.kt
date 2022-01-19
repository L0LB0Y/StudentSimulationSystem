package com.example.studentsimulationsystem.repository

import com.example.studentsimulationsystem.api.ApiClientImplementation
import com.example.studentsimulationsystem.model.Student
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiClientImplementation: ApiClientImplementation) {
    suspend fun checkStudentLogin(studentPassword: String) =
        apiClientImplementation.checkStudentLogin(studentPassword)
}