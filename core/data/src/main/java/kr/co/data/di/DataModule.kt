package kr.co.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.data.LocationRepositoryImpl
import kr.co.domain.LocationRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Singleton
    @Binds
    fun bindLocationRepository(
        impl: LocationRepositoryImpl
    ): LocationRepository

}