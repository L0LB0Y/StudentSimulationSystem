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
import com.example.studentsimulationsystem.model.Subject
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
    var dataStructure = mutableStateOf("")
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


    fun getStudentByID(studentID: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            kotlin.runCatching {
                adminRepository.getStudentByID(studentID)
            }.onSuccess {
                _student.value = it
                isLoading.value = false
                onComplete()
            }.onFailure {
                Log.d("lol", "checkUserType: ${it.message}")
                isLoading.value = false
                Toast.makeText(
                    context, "Error Or Failure Check Server Response", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    fun insertStudentInServer() {
        val checkInput = checkInputs()
        if (checkInput)
            viewModelScope.launch {
                val student = makeStudent()
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
                    }
                    clearAllVariable()
                }.onFailure {
                    isLoading.value = false
                    Toast.makeText(
                        context,
                        "Error Or Failure Check Server Response",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        else
            Toast.makeText(context, "Pleas Fill All Form", Toast.LENGTH_SHORT).show()
    }

    private fun clearAllVariable() {
        studentID.value = ""
        studentName.value = ""
        ai.value = ""
        programming.value = ""
        database.value = ""
        dataStructure.value = ""
        network.value = ""
    }

    private fun makeStudent() =
        Student(
            studentName = studentName.value,
            studentID = studentID.value,
            subject = listOf(
                Subject(subjectName = "Database", subjectDegree = database.value),
                Subject(subjectName = "ai", subjectDegree = ai.value),
                Subject(subjectName = "network", subjectDegree = network.value),
                Subject(subjectName = "dataStructure", subjectDegree = dataStructure.value),
                Subject(subjectName = "programming", subjectDegree = programming.value)
            )
        )

    private fun checkInputs() =
        studentID.value.isNotBlank() && studentName.value.isNotBlank()
                && dataStructure.value.isNotBlank() && ai.value.isNotBlank()
                && database.value.isNotBlank() && network.value.isNotBlank() && programming.value.isNotBlank()

    fun getListOfStudent() {
        isLoading.value = true
        viewModelScope.launch {
            kotlin.runCatching {
                adminRepository.getAllStudent()
            }.onSuccess {
                _students.value = it
                isLoading.value = false
            }.onFailure {
                Log.d("lol", "checkUserType: ${it.message}")
                isLoading.value = false
                Toast.makeText(
                    context,
                    "Error Or Failure Check Server Response",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun updateStudent() {
        val checkInput = checkInputs()
        if (checkInput)
            Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Pleas Fill All Form", Toast.LENGTH_SHORT).show()
    }
}