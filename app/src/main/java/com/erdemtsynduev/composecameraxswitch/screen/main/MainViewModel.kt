package com.erdemtsynduev.composecameraxswitch.screen.main

import androidx.lifecycle.ViewModel
import com.erdemtsynduev.composecameraxswitch.screen.main.ui.MainUiState
import com.erdemtsynduev.composecameraxswitch.utils.StateStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val stateStore = StateStore(MainUiState())
    val uiState: StateFlow<MainUiState> = stateStore.stateFlow

    fun changeStateCamera(stateSwitch: Boolean) {
        stateStore.updateState {
            it.copy(
                isFrontCamera = stateSwitch,
            )
        }
    }
}