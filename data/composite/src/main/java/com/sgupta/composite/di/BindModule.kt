package com.sgupta.composite.di

import com.sgupta.composite.repoimpl.aiassistant.AIAssistantRepoImpl
import com.sgupta.composite.repoimpl.reels.ReelsRepositoryImpl
import com.sgupta.composite.repoimpl.topnews.TopNewsRepoImpl
import com.sgupta.domain.repo.AIAssistantRepo
import com.sgupta.domain.repo.ReelsRepo
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

    @Binds
    abstract fun bindAIAssistantRepo(impl: AIAssistantRepoImpl): AIAssistantRepo

    @Binds
    abstract fun bindAReelsRepo(impl: ReelsRepositoryImpl): ReelsRepo
}