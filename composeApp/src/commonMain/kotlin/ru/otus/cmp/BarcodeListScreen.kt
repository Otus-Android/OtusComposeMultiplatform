package ru.otus.cmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import otuscomposemultiplatform.composeapp.generated.resources.Res
import otuscomposemultiplatform.composeapp.generated.resources.ic_qr_code
import otuscomposemultiplatform.composeapp.generated.resources.qr_code_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarcodeListScreen(
    barcodes: List<String>,
    onScanBarcodeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.qr_code_title)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onScanBarcodeClick()
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        modifier = modifier.statusBarsPadding()
    ) { contentPadding ->
        LazyColumn(
            Modifier
                .padding(contentPadding)
                .fillMaxSize(),
        ) {
            items(barcodes) { barcode ->
                ListItem(
                    leadingContent = {
                        Icon(
                            painterResource(Res.drawable.ic_qr_code),
                            contentDescription = null
                        )
                    },
                    headlineContent = { Text(barcode) }
                )
            }
        }
    }
}