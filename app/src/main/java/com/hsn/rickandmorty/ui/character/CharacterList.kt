package com.hsn.rickandmorty.ui.character

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.hsn.rickandmorty.data.viewmodels.DataSourceViewModel
import com.hsn.rickandmorty.ui.GenericList
import com.hsn.rickandmorty.ui.GenericLoadingItem

@Composable
fun CharacterList(modifier : Modifier, viewModel : DataSourceViewModel) {

    val data = viewModel.characters.collectAsState()
    val searchData = viewModel.characterSearchResult.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val isSearching = viewModel.isSearching.collectAsState()

    GenericList(modifier,list = if(isSearching.value) searchData.value else data.value ,2,isLoading.value, {
        if (viewModel.hasMoreCharacters() && !isSearching.value) {
            viewModel.getAllCharacters()
        }
    }) { index ->
        GenericLoadingItem(index = index, modifier = modifier) {
            CharacterLoadingItem(modifier = it)
        }
    }
}