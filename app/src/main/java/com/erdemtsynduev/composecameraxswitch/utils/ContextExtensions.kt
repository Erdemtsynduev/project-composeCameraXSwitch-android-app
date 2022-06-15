package com.erdemtsynduev.composecameraxswitch.utils

import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.erdemtsynduev.composecameraxswitch.model.CameraProcessData
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun Context.getCameraProvider(): CameraProcessData = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        val executor = ContextCompat.getMainExecutor(this)
        cameraProvider.addListener({
            continuation.resume(
                CameraProcessData(
                    cameraProvider = cameraProvider.get(),
                    executor = executor
                )
            )
        }, executor)
    }
}