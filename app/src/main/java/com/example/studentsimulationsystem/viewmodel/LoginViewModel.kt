package com.example.studentsimulationsystem.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentsimulationsystem.repository.LoginRepository
import com.example.studentsimulationsystem.utiles.TypeOfUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
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
            val test = loginRepository.test()
            Timber.d("sudan $test")
            Timber.d("sudan d boy")
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