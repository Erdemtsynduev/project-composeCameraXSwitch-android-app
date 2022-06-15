package com.erdemtsynduev.composecameraxswitch.screen.main

import androidx.lifecycle.ViewModel
import com.erdemtsynduev.composecameraxswitch.camerax.ui.CameraxUiState
import com.erdemtsynduev.composecameraxswitch.utils.StateStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val stateStore = StateStore(CameraxUiState())
    val uiState: StateFlow<CameraxUiState> = stateStore.stateFlow

    fun changeStateCamera(stateSwitch: Boolean) {
        stateStore.updateState {
            it.copy(
                isFrontCamera = stateSwitch,
            )
        }
    }
}