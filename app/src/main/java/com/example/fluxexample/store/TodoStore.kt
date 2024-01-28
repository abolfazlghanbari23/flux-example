package com.example.fluxexample.store

import com.example.fluxexample.action.AddTodoAction
import com.example.fluxexample.event.TodoSavedEvent
import com.example.fluxexample.flux.Event
import com.example.fluxexample.flux.FluxDispatcher
import com.example.fluxexample.flux.FluxStore
import com.example.fluxexample.flux.FluxView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TodoStore(dispatcher: FluxDispatcher) : FluxStore {

    private val subscribers = mutableSetOf<MutableSharedFlow<Event>>()
    private val disposable = dispatcher.subscribe(this)
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            disposable.collect { action ->
                if (action is AddTodoAction) {
                    subscribers.forEach { subscriber ->
                        subscriber.emit(TodoSavedEvent(action.title))
                    }
                }
            }
        }
    }

    override fun subscribe(view: FluxView): Flow<Event> {
        val flow = MutableSharedFlow<Event>()
        subscribers.add(flow)
        return flow.asSharedFlow()
    }

    override fun unsubscribe(flow: Flow<Event>) {
        subscribers.remove(flow)
    }
}