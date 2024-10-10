package kr.co.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import kr.co.data.source.remote.LocationRemoteDataSource
import kr.co.remote.LocationRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class RemoteModule {

    @Singleton
    @Provides
    fun provideLocationRemoteDataSource(
        client: HttpClient
    ): LocationRemoteDataSource = LocationRemoteDataSourceImpl(client)

}