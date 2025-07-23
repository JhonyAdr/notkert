package mamani.luna.notkert.ui.screens.catalog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mamani.luna.notkert.viewmodel.ProductViewModel
import mamani.luna.notkert.ui.components.NotkertTopAppBar
import mamani.luna.notkert.ui.components.PrimaryButton
import mamani.luna.notkert.ui.components.LoadingIndicator
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.foundation.Image
import mamani.luna.notkert.viewmodel.CartViewModel

@Composable
fun CatalogScreen(
    viewModel: ProductViewModel = viewModel(),
    onAddProduct: () -> Unit = {},
    onProductClick: (String) -> Unit = {},
    onCartClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    cartViewModel: CartViewModel
) {
    // Cargar productos al iniciar
    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }
    val products by viewModel.products
    var showLoading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            NotkertTopAppBar(
                title = "Catálogo",
                canNavigateBack = false,
                actions = {
                    IconButton(onClick = onCartClick) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                    }
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, contentDescription = "Ajustes")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddProduct) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Producto")
            }
        }
    ) { paddingValues ->
        Box(Modifier.fillMaxSize().padding(paddingValues)) {
            AnimatedVisibility(visible = showLoading, enter = fadeIn(), exit = fadeOut()) {
                LoadingIndicator(Modifier.fillMaxSize())
            }
            LazyColumn {
                items(products) { product ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onProductClick(product.id) }
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            // Mostrar imagen local si existe
                            val bitmap = remember(product.imagePath) {
                                try {
                                    BitmapFactory.decodeFile(product.imagePath)
                                } catch (e: Exception) {
                                    null
                                }
                            }
                            if (bitmap != null) {
                                Image(
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = "Imagen del producto",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                )
                            }
                            Text(product.nombre, style = MaterialTheme.typography.titleLarge)
                            Text(product.descripcion)
                            Text("Precio: $${product.precio}")
                            Text("Stock: ${product.stock}")
                            Text("Condición: ${product.condicion}")
                        }
                    }
                }
            }
        }
    }
}