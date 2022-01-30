package com.example.studentsimulationsystem.api

import com.example.studentsimulationsystem.model.Student
import com.example.studentsimulationsystem.utiles.Constant
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
                Constant.BASE_URL + Constant.INSERT_STUDENT
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
            val url = Constant.BASE_URL + Constant.LOGIN
            val response = httpClient.post<Student?>(url) {
                parameter("password", studentPassword)
            }
            response
        } catch (ex: Exception) {
            throw ex
        }
    }

    suspend fun getAllStudent(): List<Student> {
        return try {
            val url = Constant.BASE_URL + Constant.LIST_OF_STUDENT
            val response = httpClient.post<List<Student>>(url)
            response
        } catch (ex: Exception) {
            throw ex
        }

    }
}