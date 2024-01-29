package com.example.fluxexample.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluxexample.counter.CounterAction
import com.example.fluxexample.counter.CounterDispatcher
import com.example.fluxexample.counter.CounterStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
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
