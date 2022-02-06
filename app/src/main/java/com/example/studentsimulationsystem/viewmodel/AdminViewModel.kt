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
import com.example.studentsimulationsystem.utiles.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
/**
 * This Admin View Model For Control All Admin Screen
 * In Admin Home Screen Displaying List Of Student From The Server
 * In Admin Update Screen Enabling Update Student Info
 * In Admin Add Student Screen Enabling Add New Student
 **/
class AdminViewModel @Inject constructor(
    private val adminRepository: AdminRepository,
    private val context: Context
) :
    ViewModel() {

    /** These Variable For Handling Student Info*/
    var studentName = mutableStateOf("")
    var studentID = mutableStateOf("")
    var ai = mutableStateOf("")
    var network = mutableStateOf("")
    var dataStructure = mutableStateOf("")
    var database = mutableStateOf("")
    var programming = mutableStateOf("")

    /** For List Of Student From The Server*/
    private var _students = MutableLiveData<List<Student>>()
    val students: LiveData<List<Student>> = _students

    /** For Student Object That Coming From The Server */
    private var _student = MutableLiveData<Student>()
    val student: LiveData<Student> = _student

    /** List Of Student Years*/
    val years =
        listOf("First Year", "Second Year", "Third Year", "Fourth Year", "Fifth Year")

    /** List Of Student Semester*/
    val semester = listOf("Semester One", "Semester Tow")

    /** This Variable For Muting UI Until The Operation Complete*/
    var isLoading = mutableStateOf(false)


    /** Tis Function For Insert Student In Server Database*/
    fun insertStudentInServer(onComplete: () -> Unit) {
        val checkInput = checkInputs()
        if (checkInput)
            viewModelScope.launch {
                val studentBeforeEncryption = makeStudent()
                val encryptionStudent = Constant.encryptStudent(studentBeforeEncryption)
                isLoading.value = true
                kotlin.runCatching {
                    adminRepository.insertStudent(encryptionStudent)
                }.onSuccess {
                    if (it == "1") {
                        Toast.makeText(
                            context,
                            "Student Added Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        isLoading.value = false
                    }
                    onComplete()
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

    /** This Function Will Update Student Object Into Database Server*/
    fun updateStudent(onComplete: () -> Unit) {
        val checkInput = checkInputs()
        if (checkInput)
            viewModelScope.launch {
                val studentBeforeEncryption = makeStudent()
                val encryptionStudent = Constant.encryptStudent(studentBeforeEncryption)
                Log.d("sudan", "updateStudent: ${encryptionStudent.studentID}")
                isLoading.value = true
                kotlin.runCatching {
                    adminRepository.updateStudent(encryptionStudent)
                }.onSuccess {
                    Log.d("sudan", "updateStudent: $it")
                    if (it == "1") {
                        Toast.makeText(
                            context,
                            "Student Update Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        isLoading.value = false
                        onComplete()
                    } else {
                        Toast.makeText(
                            context,
                            "Student Not Update Successfully Please Check The Server",
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

    /** This Function For Clearing All Student Info Variable*/
    private fun clearAllVariable() {
        studentID.value = ""
        studentName.value = ""
        ai.value = ""
        programming.value = ""
        database.value = ""
        dataStructure.value = ""
        network.value = ""
    }

    /** This Function Will Make Student Object From His Variables*/
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

    /** This Function Will Check If The Inputs Was Correct OR Not*/
    private fun checkInputs() =
        studentID.value.isNotBlank() && studentName.value.isNotBlank()
                && dataStructure.value.isNotBlank() && ai.value.isNotBlank()
                && database.value.isNotBlank() && network.value.isNotBlank() && programming.value.isNotBlank()

    /** This Function Will Getting List Of Student From The Server*/
    fun getListOfStudent() {
        isLoading.value = true
        viewModelScope.launch {
            kotlin.runCatching {
                adminRepository.getAllStudent()
            }.onSuccess {
                val listOfDecryptionStudent = mutableListOf<Student>()
                it.forEach { student ->
                    listOfDecryptionStudent.add(Constant.decryptStudent(student))
                }
                _students.value = listOfDecryptionStudent
                isLoading.value = false
            }.onFailure {
                Log.d("sudan", "checkUserType: ${it.message}")
                isLoading.value = false
                Toast.makeText(
                    context,
                    "Error Or Failure Check Server Response",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}