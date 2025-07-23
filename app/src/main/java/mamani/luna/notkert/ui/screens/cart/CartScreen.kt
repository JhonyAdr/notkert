package mamani.luna.notkert.ui.screens.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mamani.luna.notkert.viewmodel.CartViewModel
import mamani.luna.notkert.ui.components.NotkertTopAppBar
import mamani.luna.notkert.ui.components.PrimaryButton

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(),
    onCheckout: () -> Unit
) {
    val cart by viewModel.cart
    var showSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { viewModel.loadCart() }

    Scaffold(
        topBar = {
            NotkertTopAppBar(
                title = "Carrito",
                canNavigateBack = true,
                onNavigateUp = onCheckout
            )
        }
    ) { paddingValues ->
        Column(Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            LazyColumn(Modifier.weight(1f)) {
                items(cart) { item ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column(Modifier.weight(1f)) {
                                Text(item.product.nombre, style = MaterialTheme.typography.titleLarge)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(
                                        onClick = {
                                            if (item.cantidad > 1) viewModel.updateCartItemQuantity(item.product.id, item.cantidad - 1)
                                        },
                                        enabled = item.cantidad > 1
                                    ) {
                                        Icon(Icons.Default.Remove, contentDescription = "Restar")
                                    }
                                    Text(item.cantidad.toString(), Modifier.padding(horizontal = 8.dp))
                                    IconButton(
                                        onClick = {
                                            if (item.cantidad < item.product.stock) viewModel.updateCartItemQuantity(item.product.id, item.cantidad + 1)
                                        },
                                        enabled = item.cantidad < item.product.stock
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = "Sumar")
                                    }
                                    Text("/ ${item.product.stock}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                            PrimaryButton(
                                text = "Quitar",
                                onClick = { viewModel.removeFromCart(item.product.id) },
                                modifier = Modifier.width(100.dp)
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            PrimaryButton(
                text = "Comprar",
                onClick = {
                    // Actualizar stock de cada producto en Firestore
                    cart.forEach { item ->
                        val newStock = item.product.stock - item.cantidad
                        viewModel.updateProductStock(item.product.id, newStock) { }
                    }
                    viewModel.clearCart()
                    showSuccess = true
                },
                modifier = Modifier.fillMaxWidth()
            )
            AnimatedVisibility(visible = showSuccess, enter = fadeIn(), exit = fadeOut()) {
                Text("¡Compra realizada con éxito!", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
} 