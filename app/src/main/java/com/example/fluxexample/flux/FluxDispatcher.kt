package com.example.fluxexample.flux

import kotlinx.coroutines.flow.Flow

interface FluxDispatcher {
    fun dispatch(action: FluxAction)
    fun subscribe(store: FluxStore): Flow<FluxAction>
    fun unsubscribe(flow: Flow<FluxAction>)
}