package com.example.fluxexample.flux

import kotlinx.coroutines.flow.Flow

interface FluxStore {
    fun subscribe(view: FluxView): Flow<Event>
    fun unsubscribe(flow: Flow<Event>)
}