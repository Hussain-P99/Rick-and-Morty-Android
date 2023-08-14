package com.hsn.rickandmorty.ui.episode

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hsn.rickandmorty.EpisodesQuery
import com.hsn.rickandmorty.ui.theme.RickAndMortyTheme


@Composable
fun EpisodeItem(modifier : Modifier,episode : EpisodesQuery.Result) {

    Card(modifier = modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp, horizontal = 8.dp)
        .clickable {

        },
        shape = RoundedCornerShape(10.dp)
    ) {

        Row {
            Text(modifier = modifier.padding(16.dp),text = episode.id ?: "")

            Text(modifier = modifier
                .weight(1f)
                .align(Alignment.CenterVertically), text = episode.name ?: "")

            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Rounded.KeyboardArrowRight,"")
            }
        }
    }
}

@Preview
@Composable
fun PreviewEpisodeItem() {

    val episode = EpisodesQuery.Result("1",name = "Pilot", listOf())

    RickAndMortyTheme {
        EpisodeItem(modifier = Modifier, episode = episode)
    }
}
