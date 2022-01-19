package com.example.studentsimulationsystem.api

import com.example.studentsimulationsystem.model.Student
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class ApiClientImplementation @Inject constructor(private val httpClient: HttpClient) {
    suspend fun insertFirstYearResultSimOne(
        student: Student
    ): String {
        return try {
            val gson = Gson()
            val url =
                "http://192.168.43.69:8080/StudentSimulationSystem/InsertFirstYearResultSimOne.jsp"
            val response = httpClient.post<String>(url) {
                contentType(ContentType.Application.Json)
                parameter("student", gson.toJson(student))
            }
            response
        } catch (ex: Exception) {
            throw ex
        }

    }

    suspend fun checkStudentLogin(studentPassword: String): Student? {
        return try {
            val url = "http://192.168.43.69:8080/StudentSimulationSystem/Login.jsp"
            val response = httpClient.post<Student?>(url) {
                parameter("studentPassword", studentPassword)
            }
            response
        } catch (ex: Exception) {
            throw ex
        }
    }

    suspend fun getAllStudent(): List<Student> {
        return try {
            val url = "http://192.168.43.69:8080/StudentSimulationSystem/GetAllStudent.jsp"
            val response = httpClient.post<List<Student>>(url)
            response
        } catch (ex: Exception) {
            throw ex
        }

    }
}