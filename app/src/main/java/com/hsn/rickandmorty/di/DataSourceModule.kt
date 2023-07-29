package com.hsn.rickandmorty.di

import com.apollographql.apollo3.ApolloClient
import com.hsn.rickandmorty.data.AppRepositoryImpl
import com.hsn.rickandmorty.data.remote.AppRepository
import com.hsn.rickandmorty.data.remote.RickAndMortyRepository
import com.hsn.rickandmorty.data.remote.RickAndMortyRepositoryImpl
import com.hsn.rickandmorty.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun AppRepository(rickAndMortyRepository: RickAndMortyRepository) : AppRepository {
        return AppRepositoryImpl(rickAndMortyRepository)
    }

    @Provides
    fun provideRemoteRepository(apolloClient: ApolloClient) : RickAndMortyRepository {
        return RickAndMortyRepositoryImpl(apolloClient)
    }

    @Provides
    @Singleton
    fun provideApolloClient() : ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(Constants.BASE_URL)
            .build()
    }

}