package com.example.studentsimulationsystem.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentsimulationsystem.repository.LoginRepository
import com.example.studentsimulationsystem.utiles.Constant
import com.example.studentsimulationsystem.utiles.TypeOfUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class LoginViewModel
/**
 * Login View Model For Authentication Type Of User If He is A Student Or Admin
 **/
@Inject constructor(
    private val loginRepository: LoginRepository,
    private val context: Context
) :
    ViewModel() {
    /**
     * This Variable For Mute UI Until Auth Operation Complete
     **/
    var isLoading = mutableStateOf(false)

    /**
     * This Function Will Call The Server And Check User Type
     * If User ID Start With 111 He is Admin
     * And If User ID Start With 555 He is Student
     * It Will Assigning User Type In UserType Class
     * */
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
                            TypeOfUser.student = Constant.decryptStudent(it)
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