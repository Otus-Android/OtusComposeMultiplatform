package ru.otus.cmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.window.ComposeUIViewController

val LocalNativeViewFactory = staticCompositionLocalOf<NativeViewFactory> {
    error("No native view factory provided")
}

fun MainUIViewController(
    nativeViewFactory: NativeViewFactory
) = ComposeUIViewController {
    CompositionLocalProvider(LocalNativeViewFactory provides nativeViewFactory) {
        MaterialTheme {
            BarcodesNavGraph()
        }
    }
}