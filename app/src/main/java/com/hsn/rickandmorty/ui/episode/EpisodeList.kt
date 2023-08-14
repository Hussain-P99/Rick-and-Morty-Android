package com.hsn.rickandmorty.ui.episode

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.hsn.rickandmorty.data.viewmodels.DataSourceViewModel
import com.hsn.rickandmorty.ui.GenericList
import com.hsn.rickandmorty.ui.GenericLoadingItem


@Composable
fun EpisodeList(modifier : Modifier,viewModel: DataSourceViewModel) {

    val data = viewModel.episodes.collectAsState()
    val searchData = viewModel.episodeSearchResult.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val isSearching = viewModel.isSearching.collectAsState()

    GenericList(modifier,list = if (isSearching.value) searchData.value else data.value,1,isLoading.value, {
        if (viewModel.hasMoreEpisodes() && !isSearching.value) {
            viewModel.getAllEpisodes()
        }
    }) { index ->
        GenericLoadingItem(index = index, modifier = Modifier) {
            EpisodeLoadingItem(modifier = it)
        }
    }
}