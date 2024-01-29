package com.example.fluxexample.counter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(
    private val dispatcher: CounterDispatcher,
    store: CounterStore
) : ViewModel() {
    val counter: StateFlow<Int> = store.counter

    fun increment(value: Int) {
        viewModelScope.launch {
            dispatcher.dispatch(CounterAction.Increment(value))
        }
    }

    fun decrement(value: Int) {
        viewModelScope.launch {
            dispatcher.dispatch(CounterAction.Decrement(value))
        }
    }
}
