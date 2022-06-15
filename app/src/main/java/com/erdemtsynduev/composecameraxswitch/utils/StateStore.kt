package com.erdemtsynduev.composecameraxswitch.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Common store for screen states.
 * They can be either Boolean states (which contain domain data),
 * and UI states (which contain data for direct rendering of screen elements).
 *
 * When what state to save in this class?
 * 1. For simple screens with few elements and simple logic, UI state will do.
 * 2. And for screens with complex logic, it is better to separate the UI state and the logical state. In this case, save
 * this class needs a logical VM state.
 *
 * The purpose of this class is to store the state and provide a convenient interface for updating the state.
 */
class StateStore<S>(initialState: S) {

    private val mutableStateFlow = MutableStateFlow(initialState)
    val stateFlow: StateFlow<S> = mutableStateFlow.asStateFlow()

    val value get() = stateFlow.value

    /**
     * Does an atomic state update.
     * Atomicity is achieved by using [StateFlow]
     */
    fun updateState(block: (S) -> S) {
        val currentState: S = mutableStateFlow.value
        val updatedState: S = block(currentState)

        mutableStateFlow.value = updatedState
    }
}
