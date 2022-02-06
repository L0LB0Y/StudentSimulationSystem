package com.example.studentsimulationsystem.repository

import com.example.studentsimulationsystem.api.ApiClientImplementation
import com.example.studentsimulationsystem.model.Student
import javax.inject.Inject

/** This A Repository Class For Providing Data From The Server*/
class AdminRepository @Inject constructor(private val apiClientImplementation: ApiClientImplementation) {

    /** This Function Will Getting All Student From The Server*/
    suspend fun getAllStudent() = apiClientImplementation.getAllStudent()

    /** This Function Will Get Student BY ID From The Server*/
    suspend fun getStudentByID(studentID: String) =
        apiClientImplementation.studentLogin(studentID)

    /** This Function Will Insert Student Into Server Database*/
    suspend fun insertStudent(student: Student) =
        apiClientImplementation.insertStudent(student)

    /** This Function Will Update Student Into Database Server*/
    suspend fun updateStudent(student: Student) =
        apiClientImplementation.updateStudent(student)
}