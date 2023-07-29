package com.hsn.rickandmorty.data

import com.hsn.rickandmorty.CharacterQuery
import com.hsn.rickandmorty.CharactersQuery
import com.hsn.rickandmorty.EpisodeQuery
import com.hsn.rickandmorty.EpisodesQuery
import com.hsn.rickandmorty.data.remote.AppRepository
import com.hsn.rickandmorty.data.remote.RickAndMortyRepository
import com.hsn.rickandmorty.data.remote.RickAndMortyRepositoryImpl
import com.hsn.rickandmorty.models.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppRepositoryImpl(private val rickAndMortyRepository: RickAndMortyRepository) : AppRepository {
    override suspend fun getAllCharacters(page: Int) = flow<ApiResult<CharactersQuery.Characters>> {
        emit(ApiResult.Loading())
        emit(rickAndMortyRepository.getAllCharacters(page))
    }

    override suspend fun getCharacterById(id: String) = flow<ApiResult<CharacterQuery.Character>> {
        emit(ApiResult.Loading())
        emit(rickAndMortyRepository.getCharacterById(id))
    }

    override suspend fun searchCharacter(characterName: String)= flow<ApiResult<CharactersQuery.Characters>> {
        emit(ApiResult.Loading())
        emit(rickAndMortyRepository.searchCharacter(characterName))
    }

    override suspend fun getAllEpisodes(page: Int) = flow<ApiResult<EpisodesQuery.Episodes>> {
        emit(ApiResult.Loading())
        emit(rickAndMortyRepository.getAllEpisodes(page))
    }

    override suspend fun getEpisodeById(id: String) = flow<ApiResult<EpisodeQuery.Episode>> {
        emit(ApiResult.Loading())
        emit(rickAndMortyRepository.getEpisodeById(id))
    }

    override suspend fun searchEpisode(episodeName: String) = flow<ApiResult<EpisodesQuery.Episodes>> {
        emit(ApiResult.Loading())
        emit(rickAndMortyRepository.searchEpisode(episodeName))
    }
}