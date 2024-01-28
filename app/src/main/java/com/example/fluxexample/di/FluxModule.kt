package com.example.fluxexample.di

import com.example.fluxexample.dispatcher.MainDispatcher
import com.example.fluxexample.flux.FluxDispatcher
import com.example.fluxexample.flux.FluxStore
import com.example.fluxexample.store.TodoStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FluxModule {

    @Singleton
    @Provides
    fun providesFluxDispatcher(): FluxDispatcher {
        return MainDispatcher()
    }

    @Singleton
    @Provides
    fun providesFluxStore(dispatcher: FluxDispatcher): FluxStore {
        return TodoStore(dispatcher)
    }

}