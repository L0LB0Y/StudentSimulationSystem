package com.example.studentsimulationsystem.utiles

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.studentsimulationsystem.model.Student

object Constant {
    const val BASE_URL = "http://" + "192.168.1.112" + ":8080/"
    const val LOGIN = "StudentSimulationSystem/Login.jsp"
    const val LIST_OF_STUDENT = "StudentSimulationSystem/GetAllStudent.jsp"
    const val INSERT_STUDENT = "StudentSimulationSystem/InsertFirstYearResultSimOne.jsp"

    private const val AES_KEY = "lol boy"

    fun decryptStudent(student: Student): Student {
        return student
    }

    fun encryptStudent(student: Student): Student {
        return student
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decryptText(plainText: String): String? {
        return DataEncryptionAndDecryption.encryptText(plainText, AES_KEY)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun encryptText(encryptedText: String?): String {
        return DataEncryptionAndDecryption.decryptText(encryptedText, AES_KEY)
    }
}