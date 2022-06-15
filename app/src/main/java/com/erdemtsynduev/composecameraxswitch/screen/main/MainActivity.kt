package com.erdemtsynduev.composecameraxswitch.screen.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import com.erdemtsynduev.composecameraxswitch.R
import com.erdemtsynduev.composecameraxswitch.camerax.ComposeCameraxView
import com.erdemtsynduev.composecameraxswitch.ui.theme.ComposeCameraXSwitchTheme
import com.erdemtsynduev.composecameraxswitch.ui.theme.SwitchCamera
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            setViewContent()
        } else {
            showErrorPermissionToast()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissionsCamera()
    }

    private fun checkPermissionsCamera() {
        if (permissionsCameraGranted()) {
            setViewContent()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun showErrorPermissionToast() {
        Toast.makeText(
            this,
            getString(R.string.permission_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setViewContent() {
        setContent {
            val cameraxUiState by viewModel.uiState.collectAsState()

            ComposeCameraXSwitchTheme {
                ComposeCameraxView(
                    analyzer = {

                    },
                    cameraxUiState = cameraxUiState
                )
                SwitchCamera() {
                    viewModel.changeStateCamera(it)
                }
            }
        }
    }

    private fun permissionsCameraGranted() = ContextCompat.checkSelfPermission(
        baseContext, Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}