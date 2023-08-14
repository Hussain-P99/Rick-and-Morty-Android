package com.hsn.rickandmorty.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hsn.rickandmorty.CharactersQuery
import com.hsn.rickandmorty.EpisodesQuery
import com.hsn.rickandmorty.ui.character.CharacterItem
import com.hsn.rickandmorty.ui.episode.EpisodeItem
import com.hsn.rickandmorty.ui.theme.RickAndMortyTheme
import com.hsn.rickandmorty.utils.Constants


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun<T> GenericList(
    modifier: Modifier,
    list: List<T?>,
    columns : Int,
    isLoading: Boolean,
    onLoadMoreData: () -> Unit,
    loadingItem : @Composable (index : Int) -> Unit
) {

    val listState = rememberLazyGridState()
    val isReachedEndOfList by remember {
        derivedStateOf {
            listState.isScrolledToEnd()
        }
    }


    Column(modifier = modifier) {

        if (list.isEmpty() && !isLoading) {
            Box(modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally), contentAlignment = Alignment.Center) {
                Text(text = "No Results Found", textAlign = TextAlign.Center)
            }
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(columns), state = listState) {
                itemsIndexed(list, key = { _, item -> item?.hashCode() ?: "" }) { index, item ->
                    if (item is CharactersQuery.Result) {
                        val paddingStart = if ((index) % 2 == 0) 8.dp else 4.dp
                        val paddingEnd = if ((index) % 2 == 0) 4.dp else 8.dp
                        CharacterItem(
                            modifier = Modifier.padding(
                                start = paddingStart,
                                top = 4.dp,
                                end = paddingEnd,
                                bottom = 4.dp
                            ),
                            character = item
                        )
                    }

                    if (item is EpisodesQuery.Result) {
                        EpisodeItem(modifier = Modifier, episode = item)
                    }
                }

                if (isLoading) {
                    items(Constants.LIST_LOADING_ITEMS) { index ->
                        loadingItem(index + 1)
                    }
                }
            }
        }

        if (isReachedEndOfList) {
            onLoadMoreData()
        }
    }
}

fun LazyGridState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1


@Preview
@Composable
fun CharacterListPreview() {


    RickAndMortyTheme {

    }
}