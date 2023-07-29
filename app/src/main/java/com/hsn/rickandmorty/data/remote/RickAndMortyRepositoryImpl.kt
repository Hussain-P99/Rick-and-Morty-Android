package com.hsn.rickandmorty.data.remote

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.hsn.rickandmorty.CharacterQuery
import com.hsn.rickandmorty.CharactersQuery
import com.hsn.rickandmorty.EpisodeQuery
import com.hsn.rickandmorty.EpisodesQuery
import com.hsn.rickandmorty.models.ApiResult
import com.hsn.rickandmorty.type.FilterCharacter
import com.hsn.rickandmorty.type.FilterEpisode
import com.apollographql.apollo3.api.Query

class RickAndMortyRepositoryImpl(private val apolloClient: ApolloClient) : RickAndMortyRepository {

    private suspend fun <D : Query.Data> executeQuery(query: Query<D>): ApiResult<D> {
        return try {
            val response = apolloClient.query(query).execute()
            if (response.errors != null) {
                val exception = ApolloException(response.errors.toString())
                ApiResult.Error(exception.message)
            } else {
                ApiResult.Success(response.data)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ApiResult.Error(e.localizedMessage)
        }
    }

    override suspend fun getAllCharacters(page: Int): ApiResult<CharactersQuery.Characters> {
        val query = CharactersQuery(page, null)
        return when (val result = executeQuery(query)) {
            is ApiResult.Error -> ApiResult.Error(result.message)
            is ApiResult.Loading -> ApiResult.Loading()
            is ApiResult.Success -> ApiResult.Success(result.data?.characters)
        }
    }

    override suspend fun getCharacterById(id: String): ApiResult<CharacterQuery.Character> {
        val query = CharacterQuery(id)
        return when (val result = executeQuery(query)) {
            is ApiResult.Error -> ApiResult.Error(result.message)
            is ApiResult.Loading -> ApiResult.Loading()
            is ApiResult.Success -> ApiResult.Success(result.data?.character)
        }

    }

    override suspend fun searchCharacter(characterName: String): ApiResult<CharactersQuery.Characters> {
        val query = CharactersQuery(1, FilterCharacter(Optional.present(characterName)))
        return when (val result = executeQuery(query)) {
            is ApiResult.Error -> ApiResult.Error(result.message)
            is ApiResult.Loading -> ApiResult.Loading()
            is ApiResult.Success -> ApiResult.Success(result.data?.characters)
        }

    }

    override suspend fun getAllEpisodes(page: Int): ApiResult<EpisodesQuery.Episodes> {
        val query = EpisodesQuery(page, null)
        return when (val result = executeQuery(query)) {
            is ApiResult.Error -> ApiResult.Error(result.message)
            is ApiResult.Loading -> ApiResult.Loading()
            is ApiResult.Success -> ApiResult.Success(result.data?.episodes)
        }

    }

    override suspend fun getEpisodeById(id: String): ApiResult<EpisodeQuery.Episode> {
        val query = EpisodeQuery(id)
        return when (val result = executeQuery(query)) {
            is ApiResult.Error -> ApiResult.Error(result.message)
            is ApiResult.Loading -> ApiResult.Loading()
            is ApiResult.Success -> ApiResult.Success(result.data?.episode)
        }
    }

    override suspend fun searchEpisode(episodeName: String): ApiResult<EpisodesQuery.Episodes> {
        val query = EpisodesQuery(1, FilterEpisode(Optional.present(episodeName)))
        return when (val result = executeQuery(query)) {
            is ApiResult.Error -> ApiResult.Error(result.message)
            is ApiResult.Loading -> ApiResult.Loading()
            is ApiResult.Success -> ApiResult.Success(result.data?.episodes)
        }

    }
}