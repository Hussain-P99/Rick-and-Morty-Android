package com.hsn.rickandmorty.data.viewmodels

import android.util.Log
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DataSourceViewModel @Inject constructor(private val appRepository : AppRepository) : ViewModel(){

    companion object {
        private const val TAG = "DataSourceViewModel"
    }

    private var characterPageCount = 1
    private var episodePageCount = 1

    private var hasMoreCharacters = false
    private var hasMoreEpisodes = false

    fun hasMoreCharacters() = hasMoreCharacters
    fun hasMoreEpisodes() = hasMoreEpisodes


    private val _error = MutableStateFlow<String?>(null)
    val error : StateFlow<String?> = _error.asStateFlow()

    private val _characters = MutableStateFlow<List<CharactersQuery.Result?>>(listOf())
    val characters : StateFlow<List<CharactersQuery.Result?>> = _characters.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading :StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching :StateFlow<Boolean> = _isSearching.asStateFlow()

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

    private var searchJob : Job? = null

    init {
        getAllCharacters()
        getAllEpisodes()
    }

    fun getAllCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i(TAG, "getAllCharacters: fetching page : $characterPageCount")
            hasMoreCharacters = false
            appRepository.getAllCharacters(characterPageCount).collect { result ->
                withContext(Dispatchers.Main) {
                    when(result) {
                        is ApiResult.Error, is ApiResult.Loading -> {
                            handleErrorAndLoadingResult(result)
                        }
                        is ApiResult.Success -> {
                            if(result.data?.info?.next != null) {
                                hasMoreCharacters = true
                                characterPageCount++
                            } else {
                                hasMoreCharacters = false
                            }
                            _characters.value += result.data?.results ?: listOf()
                            Log.i(TAG, "getAllCharacters: received ${result.data?.results?.size} items")
                            Log.i(TAG, "getAllCharacters: total ${_characters.value.size} items")
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
                            _character.value = result.data
                        }
                    }
                }
            }
        }
    }

    fun searchCharacter(query : String) {
        _isSearching.value = query.isNotEmpty()
        if (searchJob?.isActive == true) searchJob?.cancel()
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.searchCharacter(query).collect { result ->
                when (result) {
                    is ApiResult.Error,is ApiResult.Loading -> {
                        handleErrorAndLoadingResult(result)
                    }
                    is ApiResult.Success -> {
                        _characterSearchResult.value = result.data?.results ?: listOf()
                    }
                }
            }
        }
    }

    fun getAllEpisodes() {
        Log.i(TAG, "getAllEpisodes: fetching page : $episodePageCount")
        hasMoreEpisodes = false
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.getAllEpisodes(episodePageCount).collect { result ->
                withContext(Dispatchers.Main) {
                    when (result) {
                        is ApiResult.Error, is ApiResult.Loading -> {
                            handleErrorAndLoadingResult(result)
                        }
                        is ApiResult.Success -> {
                            if(result.data?.info?.next != null) {
                                hasMoreEpisodes = true
                                episodePageCount++
                            } else {
                                hasMoreEpisodes = false
                            }
                            _episodes.value += result.data?.results ?: listOf()
                            Log.i(TAG, "getAllEpisodes: received ${result.data?.results?.size} items")
                            Log.i(TAG, "getAllEpisodes: total ${_episodes.value.size} items")
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
                            _episode.value = result.data
                        }
                    }
                }
            }
        }
    }

    fun searchEpisode(query : String) {
        _isSearching.value = query.isNotEmpty()
        if (searchJob?.isActive == true) searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            appRepository.searchEpisode(query).collect { result ->
                when(result) {
                    is ApiResult.Error, is ApiResult.Loading -> {
                        handleErrorAndLoadingResult(result)
                    }
                    is ApiResult.Success -> {
                        _episodeSearchResult.value = result.data?.results ?: listOf()
                    }
                }

            }
        }
    }

    private fun<T> handleErrorAndLoadingResult(result : ApiResult<T>) {
        when(result) {
            is ApiResult.Error -> {
                _error.value = result.message
            }
            is ApiResult.Loading -> {
                _isLoading.value = result.isLoading
            }
            else -> {}
        }
    }

}