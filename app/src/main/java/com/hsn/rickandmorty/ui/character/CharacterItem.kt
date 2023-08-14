package com.hsn.rickandmorty.ui.character

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hsn.rickandmorty.CharactersQuery
import com.hsn.rickandmorty.R
import com.hsn.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun CharacterItem(modifier: Modifier,character : CharactersQuery.Result) {


    Card(modifier =  modifier
        .fillMaxWidth()
        .clickable {

        },
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_image),
                error = painterResource(id = R.drawable.ic_broken_image),
                contentScale = ContentScale.Crop,
                contentDescription = "",
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 280f
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp, 16.dp), contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = character.name ?: "",
                    style = TextStyle(color = Color.White, fontSize = 16.sp)
                )
            }
        }
    }
}



@Preview
@Composable
fun characterItemPreview() {
    val character = CharactersQuery.Result(
        "",
        name = "Rick and Morty",
        null
    )
    RickAndMortyTheme(darkTheme = false) {
        CharacterItem(Modifier,character = character)
    }
}