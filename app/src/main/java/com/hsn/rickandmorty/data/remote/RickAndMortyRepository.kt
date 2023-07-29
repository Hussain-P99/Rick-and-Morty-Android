package com.hsn.rickandmorty.data.remote

import com.hsn.rickandmorty.CharacterQuery
import com.hsn.rickandmorty.CharactersQuery
import com.hsn.rickandmorty.EpisodeQuery
import com.hsn.rickandmorty.EpisodesQuery
import com.hsn.rickandmorty.models.ApiResult
import kotlinx.coroutines.flow.Flow


interface RickAndMortyRepository {

    suspend fun getAllCharacters(page : Int) : ApiResult<CharactersQuery.Characters>

    suspend fun getCharacterById(id : String) : ApiResult<CharacterQuery.Character>

    suspend fun searchCharacter(characterName : String) : ApiResult<CharactersQuery.Characters>

    suspend fun getAllEpisodes(page: Int) : ApiResult<EpisodesQuery.Episodes>

    suspend fun getEpisodeById(id : String) : ApiResult<EpisodeQuery.Episode>

    suspend fun searchEpisode(episodeName : String) : ApiResult<EpisodesQuery.Episodes>
}