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
/** This Object Class For Constant Things*/
object Constant {
    /** Installing Data Store In Context Object*/
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    /** AES  Key for data store*/
    val AES_KEY_DATA_STORE = stringPreferencesKey("aesKey")

    /** RSA Private Key for data store*/
    val RSA_PRIVATE_KEY_DATA_STORE = stringPreferencesKey("rsaPrivateKey")

    /** RSA Public Key for data store*/
    val RSA_PUBLIC_KEY_DATA_STORE = stringPreferencesKey("rsaPublicKey")

    /** This URL API`s For Server Request*/
    const val BASE_URL = "http://" + "192.168.1.120" + ":8080/"
    const val LOGIN = "StudentSimulationSystem/Logoin"
    const val LIST_OF_STUDENT = "StudentSimulationSystem/GetAllStudents"
    const val INSERT_STUDENT = "StudentSimulationSystem/InsertStudent"
    const val UPDATE_STUDENT = "StudentSimulationSystem/UpdateStudent"

    /** Generation AES Key For Decryption AES Text*/
    var AES_KEY = ""


    /** Generation Private Key For Decryption RSA Text*/

    var RSA_PRIVATE_KEY = ""


    /** Generation Public Key For Encryption RSA Text*/
    var RSA_PUBLIC_KEY = ""

    /** This Function For Decryption Student Propriety*/
    private fun decryptText(encryptedText: String): String {
        return DataEncryptionAndDecryption.decryptText(encryptedText, AES_KEY, RSA_PRIVATE_KEY)
    }

    /** This Function For Encryption Student Propriety*/
    private fun encryptText(plainText: String): String {
        return DataEncryptionAndDecryption.encryptText(plainText, AES_KEY, RSA_PUBLIC_KEY) ?: ""
    }

    /** This Function For Decrypt Student Object*/
    fun decryptStudent(student: Student): Student {
        val result = mutableListOf<Subject>()
        student.subject.forEach { subject ->
            result.add(
                Subject(
                    subjectName = subject.subjectName,
                    subjectDegree = decryptText(subject.subjectDegree)
                )
            )
        }
        return Student(
            studentID = student.studentID,
            studentName = decryptText(student.studentName),
            subject = result
        )
    }

    /** This Function For Encrypt Student Object*/
    fun encryptStudent(student: Student): Student {
        val result = mutableListOf<Subject>()
        student.subject.forEach { subject ->
            result.add(
                Subject(
                    subjectName = subject.subjectName,
                    subjectDegree = encryptText(subject.subjectDegree)
                )
            )
        }
        return Student(
            studentName = encryptText(student.studentName),
            studentID = student.studentID,
            subject = result
        )
    }
}