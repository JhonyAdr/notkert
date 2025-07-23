package mamani.luna.notkert.ui.screens.addproduct

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mamani.luna.notkert.data.local.ImageStorageHelper
import mamani.luna.notkert.data.model.Product
import mamani.luna.notkert.viewmodel.ProductViewModel
import mamani.luna.notkert.ui.components.NotkertTopAppBar
import mamani.luna.notkert.ui.components.PrimaryButton
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AddProductScreen(
    viewModel: ProductViewModel = viewModel(),
    onProductAdded: () -> Unit
) {
    val context = LocalContext.current
    val nombre = remember { mutableStateOf("") }
    val descripcion = remember { mutableStateOf("") }
    val precio = remember { mutableStateOf("") }
    val categoria = remember { mutableStateOf("") }
    val condicion = remember { mutableStateOf("") }
    val stock = remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imagePath by remember { mutableStateOf("") }
    val addSuccess by viewModel.addSuccess
    val error by viewModel.error
    var showSuccess by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            if (bitmap != null) {
                imagePath = ImageStorageHelper.saveImage(context, bitmap)
            }
        }
    }

    if (addSuccess) {
        showSuccess = true
        LaunchedEffect(Unit) { onProductAdded() }
    }

    Scaffold(
        topBar = {
            NotkertTopAppBar(
                title = "Agregar Producto",
                canNavigateBack = true,
                onNavigateUp = onProductAdded
            )
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues).padding(16.dp)) {
            OutlinedTextField(
                value = nombre.value,
                onValueChange = { nombre.value = it },
                label = { Text("Nombre") }
            )
            OutlinedTextField(
                value = descripcion.value,
                onValueChange = { descripcion.value = it },
                label = { Text("Descripción") }
            )
            OutlinedTextField(
                value = precio.value,
                onValueChange = { precio.value = it },
                label = { Text("Precio") }
            )
            OutlinedTextField(
                value = categoria.value,
                onValueChange = { categoria.value = it },
                label = { Text("Categoría") }
            )
            OutlinedTextField(
                value = condicion.value,
                onValueChange = { condicion.value = it },
                label = { Text("Condición") }
            )
            OutlinedTextField(
                value = stock.value,
                onValueChange = { stock.value = it },
                label = { Text("Stock") }
            )
            PrimaryButton(
                text = "Seleccionar Imagen",
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            )
            imagePath.takeIf { it.isNotEmpty() }?.let {
                val bitmap = ImageStorageHelper.loadImage(it)
                bitmap?.let { img ->
                    Image(img.asImageBitmap(), contentDescription = null, modifier = Modifier.size(120.dp))
                }
            }
            PrimaryButton(
                text = "Agregar Producto",
                onClick = {
                    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                    viewModel.addProduct(
                        Product(
                            nombre = nombre.value,
                            descripcion = descripcion.value,
                            precio = precio.value.toDoubleOrNull() ?: 0.0,
                            categoria = categoria.value,
                            condicion = condicion.value,
                            stock = stock.value.toIntOrNull() ?: 0,
                            imagePath = imagePath,
                            userId = userId
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            if (showSuccess) {
                AnimatedVisibility(visible = showSuccess, enter = fadeIn(), exit = fadeOut()) {
                    Text("¡Producto agregado con éxito!", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
} 