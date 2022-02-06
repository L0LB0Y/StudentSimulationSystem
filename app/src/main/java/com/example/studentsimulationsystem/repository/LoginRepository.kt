package com.example.studentsimulationsystem.repository

import com.example.studentsimulationsystem.api.ApiClientImplementation
import com.example.studentsimulationsystem.model.Student
import javax.inject.Inject

/** This A Repository Class For Providing Data From The Server*/
class LoginRepository @Inject constructor(private val apiClientImplementation: ApiClientImplementation) {

    /** This Function Will Authentication Student BY ID*/
    suspend fun checkStudentLogin(studentID: String) =
        apiClientImplementation.studentLogin(studentID)
}