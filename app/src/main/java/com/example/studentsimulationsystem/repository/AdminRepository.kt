package com.example.studentsimulationsystem.repository

import com.example.studentsimulationsystem.api.ApiClientImplementation
import com.example.studentsimulationsystem.model.Student
import javax.inject.Inject

class AdminRepository @Inject constructor(private val apiClientImplementation: ApiClientImplementation) {

    suspend fun getAllStudent() = apiClientImplementation.getAllStudent()
    suspend fun getStudentByID(studentID: String) =
        apiClientImplementation.checkStudentLogin(studentID)

    suspend fun insertStudentInServer(student: Student) =
        apiClientImplementation.insertFirstYearResultSimOne(student)
}