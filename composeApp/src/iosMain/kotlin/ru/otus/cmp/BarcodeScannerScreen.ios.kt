package ru.otus.cmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController

@Composable
actual fun BarcodeScannerScreen(
    onBarcodeScan: (String) -> Unit,
    modifier: Modifier
) {
    val factory = LocalNativeViewFactory.current

    UIKitViewController(
        factory = { factory.createBarcodeScannerView(onBarcodeScan) },
        modifier = modifier.fillMaxSize()
    )
}