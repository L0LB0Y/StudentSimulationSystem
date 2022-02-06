package com.example.studentsimulationsystem.api

import com.example.studentsimulationsystem.model.Student
import com.example.studentsimulationsystem.utiles.Constant
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

/**
 * This Class For Implementation All API`s For Server Request
 **/
class ApiClientImplementation @Inject constructor(private val httpClient: HttpClient) {

    /**
     * This Function Will Insert Student Object Into Server Database
     * Have Only One Argument Its Student Encrypted Object
     * And It Return String Contain 1 If The Operation Doing Will
     **/
    suspend fun insertStudent(
        student: Student
    ): String {
        return try {
            val url =
                Constant.BASE_URL + Constant.INSERT_STUDENT
            val response = httpClient.post<String>(url) {
                contentType(ContentType.Application.Json)
                body = student
            }
            response
        } catch (ex: Exception) {
            throw ex
        }

    }

    /**
     * This Function Will Update Object Into Server Database
     * Have Only One Argument Its Student Encrypted Object
     * And It Return String Contain 1 If The Operation Doing Will
     **/
    suspend fun updateStudent(
        student: Student
    ): String {
        return try {
            val url =
                Constant.BASE_URL + Constant.UPDATE_STUDENT
            val response = httpClient.post<String>(url) {
                contentType(ContentType.Application.Json)
                body = student
            }
            response
        } catch (ex: Exception) {
            throw ex
        }

    }

    /** This Function Will Check Student Authentication In Server
     * Have Only One Argument Its Student ID Encrypted String
     * And It Return Student Object If We Found It In Server Database
     **/
    suspend fun studentLogin(studentID: String): Student? {
        return try {
            val url = Constant.BASE_URL + Constant.LOGIN
            val response = httpClient.post<Student?>(url) {
                parameter("studentID", studentID)
            }
            response
        } catch (ex: Exception) {
            throw ex
        }
    }

    /**
     * This Function Will Getting List Of Students From The Server
     * And It Return The List Of Student
     **/
    suspend fun getAllStudent(): List<Student> {
        return try {
            val url = Constant.BASE_URL + Constant.LIST_OF_STUDENT
            val response = httpClient.get<List<Student>>(url)
            response
        } catch (ex: Exception) {
            throw ex
        }

    }

//    suspend fun testServer(): Student? {
//        return try {
//            val url = Constant.BASE_URL + "StudentSimulationSystem/index"
//            val subject = listOf(
//                Subject(subjectName = "database", subjectDegree = "A+"),
//                Subject(subjectName = "ai", subjectDegree = "A"),
//                Subject(subjectName = "network", subjectDegree = "C"),
//                Subject(subjectName = "dataStructure", subjectDegree = "F"),
//                Subject(subjectName = "programming", subjectDegree = "A+")
//            )
//            val student = Student(studentName = "lol boy", studentID = "5547", subject = subject)
//            val encryptedStudent = Constant.encryptStudent(student = student)
//            val decryptedStudent = Constant.decryptStudent(encryptedStudent)
//            val response = httpClient.post<Student>(url) {
//                contentType(ContentType.Application.Json)
//                body = encryptedStudent
//            }
//            response
//        } catch (ex: Exception) {
//            throw ex
//        }
//    }
}