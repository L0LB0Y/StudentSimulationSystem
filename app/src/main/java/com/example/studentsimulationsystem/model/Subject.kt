package com.example.studentsimulationsystem.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** This Is Subject Data Class Model
 *  Have Only Tow Property
 *  1- Subject Name -> String
 *  2- Subject Degree -> String
 **/
@Serializable
data class Subject(
    @SerialName("subjectName") val subjectName: String,
    @SerialName("subjectDegree") val subjectDegree: String
)