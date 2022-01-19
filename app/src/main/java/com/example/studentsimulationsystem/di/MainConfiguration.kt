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

@Module
@InstallIn(SingletonComponent::class)
object MainConfiguration {

    // Provide Context Instance
    @Singleton
    @Provides
    fun providesContextInstance(@ApplicationContext context: Context) = context

    // Provide KTOR Instance
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

    // Provider API Client Impl
    @Singleton
    @Provides
    fun provideAPIClientImplInstance(httpClient: HttpClient) =
        ApiClientImplementation(httpClient = httpClient)
}