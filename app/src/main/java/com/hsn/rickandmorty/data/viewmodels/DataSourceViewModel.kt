package com.hsn.rickandmorty.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsn.rickandmorty.CharacterQuery
import com.hsn.rickandmorty.CharactersQuery
import com.hsn.rickandmorty.EpisodeQuery
import com.hsn.rickandmorty.EpisodesQuery
import com.hsn.rickandmorty.data.remote.AppRepository
import com.hsn.rickandmorty.models.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DataSourceViewModel @Inject constructor(private val appRepository : AppRepository) : ViewModel(){

    private var page = 1

    private var hasMoreData = true

    private fun incrementPageCount() = page++

    private fun resetPageCount() {
        page = 1
        hasMoreData = true
    }

    fun hasMoreData() = hasMoreData


    private val _error = MutableStateFlow<String?>(null)
    val error : StateFlow<String?> = _error.asStateFlow()

    private val _characters = MutableStateFlow<List<CharactersQuery.Result?>>(listOf())
    val characters : StateFlow<List<CharactersQuery.Result?>> = _characters.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading :StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _character = MutableStateFlow<CharacterQuery.Character?>(null)
    val character : StateFlow<CharacterQuery.Character?> = _character.asStateFlow()

    private val _characterSearchResult = MutableStateFlow<List<CharactersQuery.Result?>>(listOf())
    val characterSearchResult : StateFlow<List<CharactersQuery.Result?>> = _characterSearchResult.asStateFlow()

    private val _episodes = MutableStateFlow<List<EpisodesQuery.Result?>>(listOf())
    val episodes : StateFlow<List<EpisodesQuery.Result?>> = _episodes.asStateFlow()

    private val _episode = MutableStateFlow<EpisodeQuery.Episode?>(null)
    val episode : StateFlow<EpisodeQuery.Episode?> = _episode.asStateFlow()

    private val _episodeSearchResult = MutableStateFlow<List<EpisodesQuery.Result?>>(listOf())
    val episodeSearchResult : StateFlow<List<EpisodesQuery.Result?>> = _episodeSearchResult.asStateFlow()


    init {
        getAllCharacters()
    }

    fun getAllCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.getAllCharacters(page).collect { result ->
                withContext(Dispatchers.Main) {
                    when(result) {
                        is ApiResult.Error, is ApiResult.Loading -> {
                            handleErrorAndLoadingResult(result)
                        }
                        is ApiResult.Success -> {
                            _isLoading.value = false
                            if(result.data?.info?.next != null) {
                                hasMoreData = true
                                incrementPageCount()
                            } else {
                                hasMoreData = false
                            }

                            _characters.value += result.data?.results ?: listOf()
                        }
                    }
                }
            }
        }
    }

    fun getCharacterById(id : String) {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.getCharacterById(id).collect { result ->
                withContext(Dispatchers.Main) {
                    when(result) {
                        is ApiResult.Error, is ApiResult.Loading -> {
                            handleErrorAndLoadingResult(result)
                        }
                        is ApiResult.Success -> {
                            _isLoading.value = false
                            _character.value = result.data
                        }
                    }
                }
            }
        }
    }

    fun searchCharacter(query : String) {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.searchCharacter(query).collect { result ->
                when (result) {
                    is ApiResult.Error,is ApiResult.Loading -> {
                        handleErrorAndLoadingResult(result)
                    }
                    is ApiResult.Success -> {
                        _isLoading.value = false
                        _characterSearchResult.value = result.data?.results ?: listOf()
                    }
                }
            }
        }
    }

    fun getAllEpisodes() {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.getAllEpisodes(page).collect { result ->
                withContext(Dispatchers.Main) {
                    when (result) {
                        is ApiResult.Error, is ApiResult.Loading -> {
                            handleErrorAndLoadingResult(result)
                        }
                        is ApiResult.Success -> {
                            _isLoading.value = false
                            if(result.data?.info?.next != null) {
                                hasMoreData = true
                                incrementPageCount()
                            } else {
                                hasMoreData = false
                            }
                            _episodes.value = result.data?.results ?: listOf()
                        }
                    }
                }
            }
        }
    }

    fun getEpisodeById(id : String) {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.getEpisodeById(id).collect { result ->
                withContext(Dispatchers.Main) {
                    when(result) {
                        is ApiResult.Error, is ApiResult.Loading -> {
                            handleErrorAndLoadingResult(result)
                        }
                        is ApiResult.Success -> {
                            _isLoading.value = false
                            _episode.value = result.data
                        }
                    }
                }
            }
        }
    }

    fun searchEpisode(query : String) {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.searchEpisode(query).collect { result ->
                when(result) {
                    is ApiResult.Error, is ApiResult.Loading -> {
                        handleErrorAndLoadingResult(result)
                    }
                    is ApiResult.Success -> {
                        _isLoading.value = false
                        _episodeSearchResult.value = result.data?.results ?: listOf()
                    }
                }

            }
        }
    }

    private fun<T> handleErrorAndLoadingResult(result : ApiResult<T>) {
        when(result) {
            is ApiResult.Error -> {
                _isLoading.value = false
                _error.value = result.message
            }
            is ApiResult.Loading -> {
                _isLoading.value = true
            }
            else -> {}
        }
    }

}