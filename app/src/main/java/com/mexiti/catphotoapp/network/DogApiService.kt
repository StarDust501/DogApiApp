package com.mexiti.catphotoapp.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mexiti.catphotoapp.model.DogPhoto
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.thedogapi.com/v1/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface DogApiService{
    @OptIn(InternalSerializationApi::class)
    @GET("images/search")
    suspend fun getphotos(@Query("limit") limit: Int = 10): List<DogPhoto>
}

//Patron de diseño
object DogApi{
    val retrofitService:DogApiService by lazy {
        retrofit.create(DogApiService::class.java)
    }
}
