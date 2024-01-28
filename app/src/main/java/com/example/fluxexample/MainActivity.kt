package com.example.fluxexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.fluxexample.action.AddTodoAction
import com.example.fluxexample.databinding.ActivityMainBinding
import com.example.fluxexample.dispatcher.MainDispatcher
import com.example.fluxexample.event.TodoSavedEvent
import com.example.fluxexample.flux.Event
import com.example.fluxexample.flux.FluxDispatcher
import com.example.fluxexample.flux.FluxStore
import com.example.fluxexample.flux.FluxView
import com.example.fluxexample.store.TodoStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : AppCompatActivity(), FluxView {
    private lateinit var binding: ActivityMainBinding
    private val dispatcher: FluxDispatcher by lazy { MainDispatcher() }
    private val store: FluxStore by lazy { TodoStore(dispatcher) }
    private lateinit var disposable: Flow<Event>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val text = binding.etTitle.text.toString()
            val action = AddTodoAction(text)
            dispatcher.dispatch(action)
        }

        disposable = store.subscribe(this)
        lifecycleScope.launch {
            disposable.collect { event ->
                if (event is TodoSavedEvent) {
                    Toast.makeText(
                        this@MainActivity,
                        "Note with title: ${event.title} saved",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        store.unsubscribe(disposable)
    }
}