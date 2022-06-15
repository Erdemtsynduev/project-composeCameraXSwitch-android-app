package com.erdemtsynduev.composecameraxswitch.ui.theme

import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun SwitchCamera(onSwitchState: (Boolean) -> Unit) {
    val checkedState = remember { mutableStateOf(false) }
    Switch(
        checked = checkedState.value,
        onCheckedChange = {
            checkedState.value = it
            onSwitchState.invoke(it)
        }
    )
}