package com.example.studentsimulationsystem.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentsimulationsystem.model.Student
import com.example.studentsimulationsystem.repository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class AdminViewModel @Inject constructor(
    private val adminRepository: AdminRepository,
    private val context: Context
) :
    ViewModel() {

    init {
        getAllStudent()
    }

    private var _students = MutableLiveData<List<Student>>()
    val students: LiveData<List<Student>> = _students

    private var _student = MutableLiveData<Student>()
    val student: LiveData<Student> = _student
    private fun getAllStudent() {
        viewModelScope.launch {
            kotlin.runCatching {
                adminRepository.getAllStudent()
            }.onSuccess {
                _students.value = it
            }.onFailure {
                Log.d("lol", "checkUserType: ${it.message}")
                Toast.makeText(
                    context,
                    "Error Or Failure Check Server Response",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun getStudentByID(studentID: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                adminRepository.getStudentByID(studentID)
            }.onSuccess {
                _student.value = it
                onComplete()
            }.onFailure {
                Log.d("lol", "checkUserType: ${it.message}")
                Toast.makeText(
                    context,
                    "Error Or Failure Check Server Response",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}