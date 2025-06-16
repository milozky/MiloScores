package com.example.miloscores.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class MviViewModel<I : MviIntent, S : MviState> : ViewModel() {
    
    private val initialState: S by lazy { createInitialState() }
    abstract fun createInitialState(): S
    
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()
    
    abstract fun handleIntent(intent: I)
    
    protected fun setState(reduce: S.() -> S) {
        val newState = state.value.reduce()
        _state.value = newState
    }
    
    protected fun launchWithState(block: suspend () -> Unit) {
        viewModelScope.launch {
            block()
        }
    }
} 