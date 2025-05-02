package ru.otus.cmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.getString
import otuscomposemultiplatform.composeapp.generated.resources.Res
import otuscomposemultiplatform.composeapp.generated.resources.qr_code_added

@Composable
fun BarcodesNavGraph(
    modifier: Modifier = Modifier,
    viewModel: BarcodesViewModel = viewModel { BarcodesViewModel() }
) {
    val navController = rememberNavController()
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is BarcodesSideEffect.PopBackStack -> {
                    navController.popBackStack()
                    launch { snackbarHostState.showSnackbar(message = getString(Res.string.qr_code_added)) }
                }
            }
        }
    }

    Scaffold(
        modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = BarcodeList,
        ) {
            composable<BarcodeList> {
                BarcodeListScreen(
                    barcodes = state.items,
                    onScanBarcodeClick = {
                        navController.navigate(BarcodeScanner)
                    }
                )
            }
            composable<BarcodeScanner> {
                BarcodeScannerScreen(onBarcodeScan = { viewModel.onBarcodeScanned(it) })
            }
        }
    }
}


@Serializable
data object BarcodeList

@Serializable
data object BarcodeScanner