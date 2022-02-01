package com.example.studentsimulationsystem.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentsimulationsystem.model.Student
import com.example.studentsimulationsystem.model.Subject
import com.example.studentsimulationsystem.repository.LoginRepository
import com.example.studentsimulationsystem.utiles.Constant
import com.example.studentsimulationsystem.utiles.TypeOfUser
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val loginRepository: LoginRepository,
    private val context: Context
) :
    ViewModel() {
    init {
        viewModelScope.launch {
            val subject = listOf(
                Subject(subjectName = "database", subjectDegree = "A+"),
                Subject(subjectName = "ai", subjectDegree = "A"),
                Subject(subjectName = "network", subjectDegree = "C"),
                Subject(subjectName = "dataStructure", subjectDegree = "F"),
                Subject(subjectName = "programming", subjectDegree = "A+")
            )
            val student = Student(studentName = "lol boy", studentID = "5547", subject = subject)
            val encryptedStudent = Constant.encryptStudent(student = student)
            val decryptedStudent = Constant.decryptStudent(encryptedStudent)
            val gson = Gson()
            val insertStudent = loginRepository.insertTest(student)
            Log.d("lol", "insertet response ${gson.toJson(encryptedStudent) }: ")
        }
    }

    var isLoading = mutableStateOf(false)

    fun checkUserType(userPassword: String, onCompleteCheck: () -> Unit) {
        if (userPassword.startsWith("111")) {
            TypeOfUser.UserType = "Admin"
            onCompleteCheck()
        } else
            viewModelScope.launch {
                isLoading.value = true
                if (userPassword.startsWith("555")) {
                    kotlin.runCatching {
                        loginRepository.checkStudentLogin(userPassword)
                    }.onFailure {
                        Log.d("lol", "checkUserType: ${it.message}")
                        Toast.makeText(
                            context,
                            "Error Or Failure Check Server Response",
                            Toast.LENGTH_SHORT
                        ).show()
                        isLoading.value = false
                    }.onSuccess {
                        if (it != null) {
                            TypeOfUser.UserType = "Student"
                            TypeOfUser.student = it
                            isLoading.value = false
                            onCompleteCheck()
                        } else {
                            isLoading.value = false
                            Toast.makeText(context, "Invalid ID", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    isLoading.value = false
                    Toast.makeText(context, "Invalid ID", Toast.LENGTH_SHORT).show()
                }
            }
    }
}