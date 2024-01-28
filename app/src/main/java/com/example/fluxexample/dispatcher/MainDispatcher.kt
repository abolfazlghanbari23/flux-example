package com.example.fluxexample.dispatcher

import com.example.fluxexample.action.AddTodoAction
import com.example.fluxexample.flux.FluxAction
import com.example.fluxexample.flux.FluxDispatcher
import com.example.fluxexample.flux.FluxStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainDispatcher : FluxDispatcher {
    private val subscribers = mutableSetOf<MutableSharedFlow<FluxAction>>()
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun dispatch(action: FluxAction) {
        scope.launch {
            subscribers.forEach { subscriber ->
                subscriber.emit(action)
            }
        }

    }

    override fun subscribe(store: FluxStore): Flow<FluxAction> {
        val flow = MutableSharedFlow<FluxAction>()
        subscribers.add(flow)
        return flow.asSharedFlow()
    }

    override fun unsubscribe(flow: Flow<FluxAction>) {
        subscribers.remove(flow)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventReceived(action: AddTodoAction) {
        dispatch(action)
    }

}