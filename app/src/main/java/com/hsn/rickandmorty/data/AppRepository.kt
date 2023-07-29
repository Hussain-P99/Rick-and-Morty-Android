package com.hsn.rickandmorty.data.remote

import com.hsn.rickandmorty.CharacterQuery
import com.hsn.rickandmorty.CharactersQuery
import com.hsn.rickandmorty.EpisodeQuery
import com.hsn.rickandmorty.EpisodesQuery
import com.hsn.rickandmorty.models.ApiResult
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun getAllCharacters(page : Int) : Flow<ApiResult<CharactersQuery.Characters>>

    suspend fun getCharacterById(id : String) : Flow<ApiResult<CharacterQuery.Character>>

    suspend fun searchCharacter(characterName : String) : Flow<ApiResult<CharactersQuery.Characters>>

    suspend fun getAllEpisodes(page: Int) : Flow<ApiResult<EpisodesQuery.Episodes>>

    suspend fun getEpisodeById(id : String) : Flow<ApiResult<EpisodeQuery.Episode>>

    suspend fun searchEpisode(episodeName : String) : Flow<ApiResult<EpisodesQuery.Episodes>>

}