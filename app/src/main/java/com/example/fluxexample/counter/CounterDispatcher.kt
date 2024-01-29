package com.example.fluxexample.counter

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class CounterDispatcher {
    private val _actions = Channel<CounterAction>()
    val actions: Flow<CounterAction> = _actions.receiveAsFlow()

    suspend fun dispatch(action: CounterAction) {
        _actions.send(action)
    }

}
