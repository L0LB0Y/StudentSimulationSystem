package com.example.studentsimulationsystem.utiles

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.studentsimulationsystem.model.Student
import com.example.studentsimulationsystem.model.Subject

@RequiresApi(Build.VERSION_CODES.O)
object Constant {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    /** AES  Key for data store*/
    val AES_KEY_DATA_STORE = stringPreferencesKey("aesKey")
    val RSA_PRIVATE_KEY_DATA_STORE = stringPreferencesKey("rsaPrivateKey")
    val RSA_PUBLIC_KEY_DATA_STORE = stringPreferencesKey("rsaPublicKey")

    const val BASE_URL = "http://" + "192.168.1.107" + ":8080/"
    const val LOGIN = "StudentSimulationSystem/Login.jsp"
    const val LIST_OF_STUDENT = "StudentSimulationSystem/GetAllStudent.jsp"
    const val INSERT_STUDENT = "StudentSimulationSystem/InsertFirstYearResultSimOne.jsp"

    /** Generation AES Key For Decryption AES Text*/
    var AES_KEY = ""


    /** Generation Private Key For Decryption RSA Text*/

    var RSA_PRIVATE_KEY = ""


    /** Generation Public Key For Encryption RSA Text*/
    var RSA_PUBLIC_KEY = ""



    private fun decryptText(encryptedText: String): String {
        return DataEncryptionAndDecryption.decryptText(encryptedText, AES_KEY, RSA_PRIVATE_KEY)
    }

    private fun encryptText(plainText: String): String {
        return DataEncryptionAndDecryption.encryptText(plainText, AES_KEY, RSA_PUBLIC_KEY) ?: ""
    }

    fun decryptStudent(student: Student): Student {
        val result = mutableListOf<Subject>()
        student.subject.forEach { subject ->
            result.add(
                Subject(
                    subjectName = decryptText(subject.subjectName),
                    subjectDegree = decryptText(subject.subjectDegree)
                )
            )
        }
        return Student(
            studentID = decryptText(student.studentID),
            studentName = decryptText(student.studentName),
            subject = result
        )
    }

    fun encryptStudent(student: Student): Student {
        val result = mutableListOf<Subject>()
        student.subject.forEach { subject ->
            result.add(
                Subject(
                    subjectName = encryptText(subject.subjectName),
                    subjectDegree = encryptText(subject.subjectDegree)
                )
            )
        }
        return Student(
            studentName = encryptText(student.studentName),
            studentID = encryptText(student.studentID),
            subject = result
        )
    }
}