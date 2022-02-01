package com.example.studentsimulationsystem.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentsimulationsystem.repository.AdminRepository
import com.example.studentsimulationsystem.utiles.Constant
import com.example.studentsimulationsystem.utiles.Constant.AES_KEY_DATA_STORE
import com.example.studentsimulationsystem.utiles.Constant.RSA_PRIVATE_KEY
import com.example.studentsimulationsystem.utiles.Constant.RSA_PRIVATE_KEY_DATA_STORE
import com.example.studentsimulationsystem.utiles.Constant.RSA_PUBLIC_KEY
import com.example.studentsimulationsystem.utiles.Constant.RSA_PUBLIC_KEY_DATA_STORE
import com.example.studentsimulationsystem.utiles.Constant.dataStore
import com.example.studentsimulationsystem.utiles.RSADemo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
@RequiresApi(Build.VERSION_CODES.O)
class SplashViewModel
@Inject constructor(private val context: Context, private val adminRepository: AdminRepository) :
    ViewModel() {

    init {
        viewModelScope.launch {

        }
    }

    private val aesKey: Flow<String> = context.dataStore.data.map { settings ->
        settings[AES_KEY_DATA_STORE] ?: ""
    }
    private val rsaPublicKey: Flow<String> = context.dataStore.data.map { settings ->
        settings[RSA_PUBLIC_KEY_DATA_STORE] ?: ""
    }
    private val rsaPrivateKey: Flow<String> = context.dataStore.data.map { settings ->
        settings[RSA_PRIVATE_KEY_DATA_STORE] ?: ""
    }

    /** Instance From RSA Demo Class For Generation Keys*/
    private val keyPairGenerator = RSADemo()

    fun start() {
        insertAESKey()
        insertRSAPrivetKey()
        insertRSAPublicKey()
    }

    private fun insertRSAPublicKey() {
        viewModelScope.launch {
            rsaPublicKey.collect { value ->
                if (value.isNotEmpty()) {
                    RSA_PUBLIC_KEY = value
                } else {
                    context.dataStore.edit { setting ->
                        setting[RSA_PUBLIC_KEY_DATA_STORE] =
                            Base64.getEncoder().encodeToString(keyPairGenerator.publicKey.encoded)
                    }
                }
            }
        }
    }

    private fun insertRSAPrivetKey() {
        viewModelScope.launch {
            rsaPrivateKey.collect { value ->
                if (value.isNotEmpty()) {
                    RSA_PRIVATE_KEY = value
                } else {
                    context.dataStore.edit { setting ->
                        setting[RSA_PRIVATE_KEY_DATA_STORE] =
                            Base64.getEncoder().encodeToString(keyPairGenerator.privateKey.encoded)
                    }
                }
            }
        }
    }

    private fun insertAESKey() {
        viewModelScope.launch {
            aesKey.collect { value ->
                if (value.isNotEmpty()) {
                    Constant.AES_KEY = value
                } else {
                    context.dataStore.edit { settings ->
                        settings[AES_KEY_DATA_STORE] = "lol d boy"
                    }
                }
            }
        }
    }
}