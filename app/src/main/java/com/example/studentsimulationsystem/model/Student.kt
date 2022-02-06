package com.example.studentsimulationsystem.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** This Is Student Data Class Model
 *  Have Only Three Property
 *  1- Student Name -> String
 *  2- Student ID -> String
 *  3- List Of Subjects Object -> Subject
 **/
@Serializable
data class Student(
    @SerialName("studentID") val studentID: String = "5552256485",
    @SerialName("studentName") val studentName: String = "Mohammed Maki",
    @SerialName("subject") val subject: List<Subject> = emptyList(),
)