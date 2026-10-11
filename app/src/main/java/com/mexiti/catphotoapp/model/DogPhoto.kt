package com.mexiti.catphotoapp.model

import android.annotation.SuppressLint
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable

data class DogPhoto(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)
