package com.omagrahari.navitest.di

import com.omagrahari.navitest.data.repository.NaviRepository
import com.omagrahari.navitest.data.repository.impl.NaviRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepoModule {

    @Binds
    abstract fun bindRepository(impl: NaviRepositoryImpl): NaviRepository

}