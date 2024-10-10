package kr.co.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kr.co.common.model.KamoException
import kr.co.kamo.core.remote.BuildConfig
import kr.co.remote.model.ApiError
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient =
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                    explicitNulls = false
                })
            }

            install(Logging) {
                level = if (BuildConfig.DEBUG) {
                    LogLevel.ALL
                } else {
                    LogLevel.NONE
                }

                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.tag("NetworkModule: Logging").d(message)
                    }
                }
            }

            install(DefaultRequest) {
                url(BASE_URL)

                headers {
                    append(HttpHeaders.Authorization, BuildConfig.KAKAO_MAP_KEY)
                }
            }

            HttpResponseValidator {
                validateResponse { response ->
                    if (!response.status.isSuccess()) {
                        val e = runCatching {
                            Json.decodeFromString<ApiError>(response.bodyAsText())
                        }.getOrElse {
                            throw it
                        }

                        throw KamoException(code = e.code, message = e.message)
                    }
                }
            }
        }

    private companion object {
        const val BASE_URL = "https://taxi-openapi.sandbox.onkakao.net"
    }
}