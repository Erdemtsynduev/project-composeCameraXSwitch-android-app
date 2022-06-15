package com.erdemtsynduev.composecameraxswitch.model

import androidx.camera.lifecycle.ProcessCameraProvider
import java.util.concurrent.Executor

data class CameraProcessData(
    val cameraProvider: ProcessCameraProvider,
    val executor: Executor,
)
