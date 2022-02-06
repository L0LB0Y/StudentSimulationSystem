package com.example.studentsimulationsystem.di

import android.content.Context
import com.example.studentsimulationsystem.api.ApiClientImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import javax.inject.Singleton

/** Hilt Module For Injection All Important Instances In The App*/
@Module
@InstallIn(SingletonComponent::class)
object MainConfiguration {

    /**
     * This Function Will Provide Context Instance
     **/
    @Singleton
    @Provides
    fun providesContextInstance(@ApplicationContext context: Context) = context

    /**
     * This Function Will Provide KTOR Instance
     * Client Network Request
     **/
    @Singleton
    @Provides
    fun providesKTORInstance() = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    /** This Function Will Provide ApiClientImplementation Instance
     * It Contain All Functions That Should Request And Response Data From The Server
     **/
    @Singleton
    @Provides
    fun provideAPIClientImplInstance(httpClient: HttpClient) =
        ApiClientImplementation(httpClient = httpClient)
}