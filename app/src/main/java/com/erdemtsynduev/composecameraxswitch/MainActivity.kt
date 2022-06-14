package com.erdemtsynduev.composecameraxswitch

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.erdemtsynduev.composecameraxswitch.camerax.SimpleCameraPreview
import com.erdemtsynduev.composecameraxswitch.ui.theme.ComposeCameraXSwitchTheme

class MainActivity : ComponentActivity() {

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
            ComposeCameraXSwitchTheme {
                SimpleCameraPreview({

                })
            }
        }
    }

    private fun permissionsCameraGranted() = ContextCompat.checkSelfPermission(
        baseContext, Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}