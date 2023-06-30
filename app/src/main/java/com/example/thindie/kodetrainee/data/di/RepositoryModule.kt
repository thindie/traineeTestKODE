package com.example.thindie.kodetrainee.data.di

import com.example.thindie.kodetrainee.data.RepositoryImpl
import com.example.thindie.kodetrainee.domain.ConcreteEmployeeRepository
import com.example.thindie.kodetrainee.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

    @Binds
    fun bindConcreteRepository(impl: RepositoryImpl): ConcreteEmployeeRepository
}