package com.mexiti.catphotoapp.ui.screens

import android.media.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mexiti.catphotoapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mexiti.catphotoapp.model.DogPhoto
import com.mexiti.catphotoapp.viewmodel.DogUiState
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
@Composable
fun HomeScreen(
    dogUiState: DogUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (dogUiState) {
        is DogUiState.Success -> PhotosGridScreen(photos = dogUiState.photos,
            modifier = modifier,
            contentPadding = contentPadding
            )
        is DogUiState.Loading -> LoadingScreen(modifier = modifier)
        is DogUiState.Error -> Text("Error")
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Image (painter = painterResource(id =R.drawable.gemini_generated_image_zicwq9zicwq9zicw_removebg_preview),
            contentDescription = "Loding")
    }
}


@Composable
fun ResultScreen(
    photos: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(text = photos)
    }
}

@Composable
@OptIn(InternalSerializationApi::class)
fun DogPhotoCard(photo: DogPhoto, modifier: Modifier = Modifier){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(photo.url)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.dog_photo),
            modifier = modifier,
            error = painterResource(R.drawable.download__21__removebg_preview),
            placeholder = painterResource(R.drawable.______o40_removebg_preview),
            contentScale = ContentScale.Fit
        )
    }
}

@OptIn(InternalSerializationApi::class)
@Composable
fun PhotosGridScreen(
    photos: List<DogPhoto>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding
    ){
        items(
            items = photos,
            key = {photo -> photo.id}
        ){ photo ->
            DogPhotoCard(
                photo = photo,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
            )
        }
    }
}

/*
@Preview
@Composable
fun HomeScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        HomeScreen(
            dogUiState = DogUiState.Success(stringResource(R.string.placeholder_result)),
        )
    }
}*/


