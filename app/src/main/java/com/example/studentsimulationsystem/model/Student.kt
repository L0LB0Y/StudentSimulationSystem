package com.example.studentsimulationsystem.model

import kotlinx.serialization.Serializable

@Serializable
data class Student(
    val studentID: String = "5552256485",
    val studentName: String = "Mohammed Maki",
    val subject: List<Subject> = emptyList(),
)