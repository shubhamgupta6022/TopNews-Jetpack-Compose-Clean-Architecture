package com.sgupta.composite.di

import com.sgupta.composite.repoimpl.TopNewsRepoImpl
import com.sgupta.domain.repo.TopNewsRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class BindModule {

    @Binds
    abstract fun bindTopNewsRepo(impl: TopNewsRepoImpl): TopNewsRepo
}