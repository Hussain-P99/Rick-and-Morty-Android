package com.hsn.rickandmorty.data

import com.hsn.rickandmorty.CharacterQuery
import com.hsn.rickandmorty.CharactersQuery
import com.hsn.rickandmorty.EpisodeQuery
import com.hsn.rickandmorty.EpisodesQuery
import com.hsn.rickandmorty.data.remote.AppRepository
import com.hsn.rickandmorty.data.remote.RickAndMortyRepository
import com.hsn.rickandmorty.models.ApiResult
import kotlinx.coroutines.flow.flow

class AppRepositoryImpl(private val rickAndMortyRepository: RickAndMortyRepository) : AppRepository {
    override suspend fun getAllCharacters(page: Int) = flow<ApiResult<CharactersQuery.Characters>> {
        emit(ApiResult.Loading())
        emit(rickAndMortyRepository.getAllCharacters(page))
        emit(ApiResult.Loading(false))
    }

    override suspend fun getCharacterById(id: String) = flow<ApiResult<CharacterQuery.Character>> {
        emit(ApiResult.Loading())
        emit(rickAndMortyRepository.getCharacterById(id))
        emit(ApiResult.Loading(false))
    }

    override suspend fun searchCharacter(characterName: String)= flow<ApiResult<CharactersQuery.Characters>> {
        emit(ApiResult.Loading())
        emit(rickAndMortyRepository.searchCharacter(characterName))
        emit(ApiResult.Loading(false))
    }

    override suspend fun getAllEpisodes(page: Int) = flow<ApiResult<EpisodesQuery.Episodes>> {
        emit(ApiResult.Loading())
        emit(rickAndMortyRepository.getAllEpisodes(page))
        emit(ApiResult.Loading(false))
    }

    override suspend fun getEpisodeById(id: String) = flow<ApiResult<EpisodeQuery.Episode>> {
        emit(ApiResult.Loading())
        emit(rickAndMortyRepository.getEpisodeById(id))
        emit(ApiResult.Loading(false))
    }

    override suspend fun searchEpisode(episodeName: String) = flow<ApiResult<EpisodesQuery.Episodes>> {
        emit(ApiResult.Loading())
        emit(rickAndMortyRepository.searchEpisode(episodeName))
        emit(ApiResult.Loading(false))
    }
}