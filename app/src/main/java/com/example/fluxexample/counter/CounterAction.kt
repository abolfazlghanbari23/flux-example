package com.example.fluxexample.counter

sealed class CounterAction {
    data class Increment(val value: Int) : CounterAction()
    data class Decrement(val value: Int) : CounterAction()
}
