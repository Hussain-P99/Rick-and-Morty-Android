package com.hsn.rickandmorty.ui.character

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hsn.rickandmorty.R
import com.hsn.rickandmorty.ui.theme.RickAndMortyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetails(modifier : Modifier) {

    Scaffold(topBar = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(vertical = 8.dp, horizontal = 4.dp)
        ) {
            IconButton(onClick = {  }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigate Back"
                )
            }
        }
    }) { contentPadding ->

        Column(
            modifier
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_image),
                error = painterResource(id = R.drawable.ic_broken_image),
                contentScale = ContentScale.Crop,
                contentDescription = "",
            )






        }

    }

}


@Preview
@Composable
fun PreviewCharacterDetails() {
    RickAndMortyTheme {
        CharacterDetails(modifier = Modifier)
    }
}