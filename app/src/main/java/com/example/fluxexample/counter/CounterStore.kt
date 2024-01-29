package com.example.fluxexample.counter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CounterStore(dispatcher: CounterDispatcher) {
    private val _counter = MutableStateFlow(0)
    val counter: StateFlow<Int> = _counter

    private val scope = CoroutineScope(Dispatchers.Main)

    init {
        scope.launch {
            dispatcher.actions.collect { counterAction ->
                when (counterAction) {
                    is CounterAction.Increment -> increment(counterAction.value)
                    is CounterAction.Decrement -> decrement(counterAction.value)
                }
            }
        }
    }

    private fun increment(value: Int) {
        _counter.value = _counter.value + value
    }

    private fun decrement(value: Int) {
        _counter.value = _counter.value - value
    }
}
