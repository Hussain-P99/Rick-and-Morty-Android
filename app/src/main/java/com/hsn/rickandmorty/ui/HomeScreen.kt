package com.hsn.rickandmorty.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hsn.rickandmorty.data.viewmodels.DataSourceViewModel
import com.hsn.rickandmorty.ui.character.CharacterList
import com.hsn.rickandmorty.ui.episode.EpisodeList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier, viewModel: DataSourceViewModel) {

    var currentTab by remember { mutableStateOf(0) }

    val tabs = listOf("Characters","Episodes")

    var searchText by remember { mutableStateOf("") }
    Scaffold { contentPadding ->
        Column(modifier = modifier.padding(contentPadding)) {

            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp,16.dp),
                value = searchText,
                placeholder = { Text("Search")},
                onValueChange = { newText ->
                    searchText = newText.trim()
                    if (currentTab == 0) {
                        viewModel.searchCharacter(searchText)
                    } else {
                        viewModel.searchEpisode(searchText)
                    }
                },
                trailingIcon = { Icon(Icons.Default.Search,"")}
            )

            TabRow(selectedTabIndex = currentTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = {Text(title)},
                        selected = currentTab == index,
                        onClick = { currentTab = index }
                    )
                }
            }

            if (currentTab == 0) {
                CharacterList(modifier = modifier, viewModel = viewModel)
            } else {
                EpisodeList(modifier = modifier, viewModel = viewModel)
            }

        }

    }

}

