package com.example.fluxexample.di

import com.example.fluxexample.counter.CounterDispatcher
import com.example.fluxexample.counter.CounterStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FluxModule {

    @Singleton
    @Provides
    fun providesCounterDispatcher(): CounterDispatcher {
        return CounterDispatcher()
    }

    @Singleton
    @Provides
    fun providesCounterStore(
        @MainDispatcher coroutineDispatcher: CoroutineDispatcher,
        dispatcher: CounterDispatcher
    ): CounterStore {
        return CounterStore(coroutineDispatcher, dispatcher)
    }

}