package com.hsn.rickandmorty.ui.episode

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hsn.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun EpisodeLoadingItem(modifier : Modifier) {

    Card(modifier = modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(10.dp)
    ) {

        Box(modifier = modifier
            .fillMaxWidth()
            .height(56.dp)) {

        }
    }

}

@Preview
@Composable
fun PreviewEpisodeLoadingItem() {
    RickAndMortyTheme {
        EpisodeLoadingItem(modifier = Modifier)
    }
}