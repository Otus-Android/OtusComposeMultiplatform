package ru.otus.cmp

import platform.UIKit.UIViewController

interface NativeViewFactory {
    fun createBarcodeScannerView(
        onDetected: (String) -> Unit
    ): UIViewController
}