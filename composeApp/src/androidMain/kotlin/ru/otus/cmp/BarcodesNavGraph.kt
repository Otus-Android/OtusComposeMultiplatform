package ru.otus.cmp

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Composable
fun BarcodesNavGraph(
    modifier: Modifier = Modifier,
    viewModel: BarcodesViewModel = viewModel()
) {
    val navController = rememberNavController()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is BarcodesSideEffect.PopBackStack -> {
                    Toast.makeText(context, R.string.qr_code_added, Toast.LENGTH_LONG).show()
                    navController.popBackStack()
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = BarcodeList,
        modifier = modifier
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


@Serializable
data object BarcodeList

@Serializable
data object BarcodeScanner