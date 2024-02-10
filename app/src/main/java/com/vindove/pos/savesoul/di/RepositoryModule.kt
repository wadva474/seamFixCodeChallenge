package com.vindove.pos.savesoul.di

import com.vindove.pos.savesoul.data.repository.SosRepositoryImpl
import com.vindove.pos.savesoul.domain.repository.SosRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSosRepository(dataStoreRepository: SosRepositoryImpl): SosRepository
}