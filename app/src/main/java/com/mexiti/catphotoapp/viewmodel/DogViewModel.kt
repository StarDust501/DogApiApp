@file:OptIn(InternalSerializationApi::class)

package com.mexiti.catphotoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexiti.catphotoapp.model.DogPhoto
import com.mexiti.catphotoapp.network.DogApi
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

sealed interface DogUiState {
    data class Success(val photos: List<DogPhoto>) : DogUiState
    object Error : DogUiState
    object Loading : DogUiState
}

class DogViewModel : ViewModel() {
    var dogUiState: DogUiState by mutableStateOf(DogUiState.Loading)
        private set

    @OptIn(InternalSerializationApi::class)
    fun getDogPhotos() {
        viewModelScope.launch {
            try {
                // TheDogAPI free tier limits single requests to 10 images max.
                // We make multiple requests (10+10+10+10 = 40 photos) to ensure a long scrollable list.
                val batch1 = DogApi.retrofitService.getphotos(10)
                val batch2 = DogApi.retrofitService.getphotos(10)
                val batch3 = DogApi.retrofitService.getphotos(10)
                val batch4 = DogApi.retrofitService.getphotos(10)
                val combinedPhotos = (batch1 + batch2 + batch3 + batch4).distinctBy { it.id }
                dogUiState = DogUiState.Success(combinedPhotos)
            } catch (e: Exception) {
                dogUiState = DogUiState.Error
            }
        }
    }
    init{
        getDogPhotos()
    }
}
