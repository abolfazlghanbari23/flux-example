package com.example.fluxexample.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fluxexample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.counter.collect { count ->
                binding.counterTextView.text = count.toString()
            }
        }

        binding.incrementButton.setOnClickListener {
            val value =
                binding.edittext.text.toString().let { if (it.isNotEmpty()) it.toInt() else 0 }
            viewModel.increment(value)
        }

        binding.decrementButton.setOnClickListener {
            val value =
                binding.edittext.text.toString().let { if (it.isNotEmpty()) it.toInt() else 0 }
            viewModel.decrement(value)
        }
    }
}