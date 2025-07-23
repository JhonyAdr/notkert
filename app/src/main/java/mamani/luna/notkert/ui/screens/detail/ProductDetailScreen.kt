package mamani.luna.notkert.ui.screens.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mamani.luna.notkert.ui.components.NotkertTopAppBar
import mamani.luna.notkert.ui.components.PrimaryButton
import mamani.luna.notkert.viewmodel.CartViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.firestore.FirebaseFirestore
import mamani.luna.notkert.data.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(navController: NavController, productId: String, cartViewModel: CartViewModel) {
    var product by remember { mutableStateOf<Product?>(null) }
    var showAdded by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    // Cargar el producto real de Firestore
    LaunchedEffect(productId) {
        loading = true
        FirebaseFirestore.getInstance().collection("productos").document(productId)
            .get()
            .addOnSuccessListener { doc ->
                product = doc.toObject(Product::class.java)?.copy(id = doc.id)
                loading = false
            }
            .addOnFailureListener {
                error = "No se pudo cargar el producto"
                loading = false
            }
    }

    Scaffold(
        topBar = {
            NotkertTopAppBar(
                title = product?.nombre ?: "Detalle",
                canNavigateBack = true,
                onNavigateUp = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        Column(Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            when {
                loading -> {
                    CircularProgressIndicator()
                }
                error != null -> {
                    Text(error!!, color = MaterialTheme.colorScheme.error)
                }
                product == null -> {
                    Text("Producto no encontrado", color = MaterialTheme.colorScheme.error)
                }
                else -> {
                    // Imagen
                    val bitmap = remember(product!!.imagePath) {
                        try {
                            BitmapFactory.decodeFile(product!!.imagePath)
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
                                .height(220.dp)
                        )
                        Spacer(Modifier.height(16.dp))
                    }
                    // Nombre
                    Text(product!!.nombre, style = MaterialTheme.typography.headlineLarge)
                    Spacer(Modifier.height(8.dp))
                    // Descripción
                    Text(product!!.descripcion, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(8.dp))
                    // Precio
                    Text("Precio: $${"%.2f".format(product!!.precio)}", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.height(8.dp))
                    // Stock y condición
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Stock: ${product!!.stock}", style = MaterialTheme.typography.bodyMedium)
                        Text("Condición: ${product!!.condicion}", style = MaterialTheme.typography.bodyMedium)
                    }
                    Spacer(Modifier.height(16.dp))
                    PrimaryButton(
                        text = "Agregar al carrito",
                        onClick = {
                            if (product!!.stock > 0) {
                                cartViewModel.addToCart(product!!)
                                cartViewModel.updateProductStock(product!!.id, product!!.stock - 1) { success ->
                                    if (success) {
                                        showAdded = true
                                        // Recargar producto para actualizar stock
                                        FirebaseFirestore.getInstance().collection("productos").document(productId)
                                            .get()
                                            .addOnSuccessListener { doc ->
                                                product = doc.toObject(Product::class.java)?.copy(id = doc.id)
                                            }
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    AnimatedVisibility(visible = showAdded, enter = fadeIn(), exit = fadeOut()) {
                        Text("¡Producto añadido al carrito!", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
} 