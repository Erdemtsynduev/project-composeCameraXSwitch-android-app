package com.erdemtsynduev.composecameraxswitch.camerax

import android.content.Context
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.erdemtsynduev.composecameraxswitch.camerax.ui.CameraxUiState
import com.erdemtsynduev.composecameraxswitch.utils.getCameraProvider
import java.util.concurrent.Executor

@Composable
fun ComposeCameraxView(
    analyzer: ImageAnalysis.Analyzer,
    cameraxUiState: CameraxUiState,
) {
    ComposeCameraxPreview(
        analyzer = analyzer,
        lensFacing = if (cameraxUiState.isFrontCamera) {
            CameraSelector.LENS_FACING_FRONT
        } else {
            CameraSelector.LENS_FACING_BACK
        },
    )
}

@Composable
fun ComposeCameraxPreview(
    analyzer: ImageAnalysis.Analyzer,
    lensFacing: Int = CameraSelector.LENS_FACING_BACK,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    val previewView = remember { PreviewView(context) }

    LaunchedEffect(lensFacing) {
        bindPreview(
            context = context,
            lifecycleOwner = lifecycleOwner,
            preview = preview,
            previewView = previewView,
            analyzer = analyzer,
            cameraSelector = cameraSelector
        )
    }

    AndroidView(
        factory = { previewView },
        modifier = Modifier.fillMaxSize(),
    )
}

suspend fun bindPreview(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    preview: Preview,
    previewView: PreviewView,
    analyzer: ImageAnalysis.Analyzer,
    cameraSelector: CameraSelector,
) {
    val cameraProcessData = context.getCameraProvider()
    cameraProcessData.cameraProvider.unbindAll()
    cameraProcessData.cameraProvider.bindToLifecycle(
        lifecycleOwner,
        cameraSelector,
        setupImageAnalysis(cameraProcessData.executor, analyzer),
        preview,
    )
    preview.setSurfaceProvider(previewView.surfaceProvider)
}

private fun setupImageAnalysis(
    executor: Executor,
    analyzer: ImageAnalysis.Analyzer
): ImageAnalysis {
    return ImageAnalysis.Builder()
        .setTargetResolution(Size(720, 1280))
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()
        .apply {
            setAnalyzer(executor, analyzer)
        }
}