package ru.otus.cmp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun BarcodeScannerScreen(
    onBarcodeScan: (String) -> Unit,
    modifier: Modifier = Modifier
)