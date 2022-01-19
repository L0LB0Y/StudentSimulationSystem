package com.example.studentsimulationsystem.repository

import com.example.studentsimulationsystem.api.ApiClientImplementation
import javax.inject.Inject

class AdminRepository @Inject constructor(private val apiClientImplementation: ApiClientImplementation) {

    suspend fun getAllStudent() = apiClientImplementation.getAllStudent()
    suspend fun getStudentByID(studentID: String) =
        apiClientImplementation.checkStudentLogin(studentID)
}