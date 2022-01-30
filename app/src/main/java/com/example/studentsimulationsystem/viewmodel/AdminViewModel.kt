package com.example.studentsimulationsystem.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentsimulationsystem.model.Student
import com.example.studentsimulationsystem.model.Subject
import com.example.studentsimulationsystem.repository.AdminRepository
import com.example.studentsimulationsystem.utiles.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class AdminViewModel @Inject constructor(
    private val adminRepository: AdminRepository,
    private val context: Context
) :
    ViewModel() {


    var isLoading = mutableStateOf(false)
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


    fun insertStudentInServer(
        studentName: String,
        studentID: String,
        database: String,
        ai: String,
        network: String,
        programming: String,
        dataStructure: String,
        onInsertComplete: () -> Unit
    ) {
        val checkIfInputsWasCorrect =
            checkInputs(studentName, studentID, database, ai, network, programming, dataStructure)
        if (checkIfInputsWasCorrect)
            viewModelScope.launch {
                isLoading.value = true
                val subjects = listOf(
                    Subject(subjectName = "database", subjectDegree = database),
                    Subject(subjectName = "ai", subjectDegree = ai),
                    Subject(subjectName = "network", subjectDegree = network),
                    Subject(subjectName = "programming", subjectDegree = programming),
                    Subject(subjectName = "dataStructure", subjectDegree = dataStructure),
                )
                val student =
                    Constant.encryptStudent(Student(studentID = studentID, studentName = studentName, subject = subjects))
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
                    Toast.makeText(
                        context,
                        "Error Or Failure Check Server Response",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        else
            Toast.makeText(
                context,
                "Please Fill The Form Correctly",
                Toast.LENGTH_SHORT
            ).show()
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