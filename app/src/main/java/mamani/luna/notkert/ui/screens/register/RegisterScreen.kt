package mamani.luna.notkert.ui.screens.register

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mamani.luna.notkert.viewmodel.AuthViewModel
import mamani.luna.notkert.ui.components.NotkertTopAppBar
import mamani.luna.notkert.ui.components.PrimaryButton

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = viewModel(),
    onRegisterSuccess: () -> Unit,
    onBack: () -> Unit = {}
) {
    val nombre = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val error by viewModel.error
    val isLoggedIn by viewModel.isLoggedIn

    if (isLoggedIn) {
        LaunchedEffect(Unit) { onRegisterSuccess() }
    }

    Scaffold(
        topBar = {
            NotkertTopAppBar(
                title = "Registro",
                canNavigateBack = true,
                onNavigateUp = onBack
            )
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues).padding(16.dp)) {
            OutlinedTextField(
                value = nombre.value,
                onValueChange = { nombre.value = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            PrimaryButton(
                text = "Registrarse",
                onClick = { viewModel.register(nombre.value, email.value, password.value) },
                modifier = Modifier.fillMaxWidth()
            )
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        }
    }
} 