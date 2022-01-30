package com.example.studentsimulationsystem.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
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

    var studentName = mutableStateOf("")
    var studentID = mutableStateOf("")
    var ai = mutableStateOf("")
    var network = mutableStateOf("")
    var dataStrucuer = mutableStateOf("")
    var database = mutableStateOf("")
    var programming = mutableStateOf("")

    /** For List Of Student*/
    private var _students = MutableLiveData<List<Student>>()
    val students: LiveData<List<Student>> = _students

    /** For One Student Object */
    private var _student = MutableLiveData<Student>()
    val student: LiveData<Student> = _student

    /** List Of Years*/
    val years =
        listOf("First Year", "Second Year", "Third Year", "Fourth Year", "Fifth Year")

    /** List Of Semester*/
    val semester = listOf("Semester One", "Semester Tow")

    var isLoading = mutableStateOf(false)


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
                    context, "Error Or Failure Check Server Response", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    fun insertStudentInServer(
        student: Student,
        onInsertComplete: () -> Unit
    ) {
        viewModelScope.launch {
            isLoading.value = true
            kotlin.runCatching {
                adminRepository.insertStudentInServer(student)
            }.onSuccess {
                if (it.trim() == "1") {
                    Toast.makeText(
                        context,
                        "Student Added Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    isLoading.value = false
                    onInsertComplete()
                }
            }.onFailure {
                isLoading.value = false
                Toast.makeText(
                    context,
                    "Error Or Failure Check Server Response",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun checkInputs(
        studentName: String,
        studentID: String,
        database: String,
        ai: String,
        network: String,
        programming: String,
        dataStructure: String,
    ) =
        studentID.isNotBlank() && studentName.isNotBlank()
                && dataStructure.isNotBlank() && ai.isNotBlank()
                && database.isNotBlank() && network.isNotBlank() && programming.isNotBlank()

    fun getListOfStudent() {
        getAllStudent()
    }
}