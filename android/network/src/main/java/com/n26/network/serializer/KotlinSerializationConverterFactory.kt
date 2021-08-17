package com.n26.network.serializer

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter

@ExperimentalSerializationApi
fun defaultConverter(
    isLenient: Boolean = false,
    serializersModuleBuilder: SerializersModuleBuilder.() -> Unit = {},
): Converter.Factory {
    val contentType = "application/json".toMediaType()
    return Json(
        builderAction = {
            coerceInputValues = true
            ignoreUnknownKeys = true
            this.isLenient = isLenient

            serializersModule = SerializersModule { serializersModuleBuilder() }
        }
    ).asConverterFactory(contentType)
}
