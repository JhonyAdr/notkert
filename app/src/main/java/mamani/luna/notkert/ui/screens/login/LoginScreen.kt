package mamani.luna.notkert.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
fun LoginScreen(
    viewModel: AuthViewModel = viewModel(),
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit = {}
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val error by viewModel.error
    val isLoggedIn by viewModel.isLoggedIn

    if (isLoggedIn) {
        LaunchedEffect(Unit) { onLoginSuccess() }
    }

    Scaffold(
        topBar = {
            NotkertTopAppBar(title = "Iniciar sesión", canNavigateBack = false)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onRegisterClick) {
                Icon(Icons.Default.Person, contentDescription = "Registrarse")
            }
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues).padding(16.dp)) {
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
                text = "Entrar",
                onClick = { viewModel.login(email.value, password.value) },
                modifier = Modifier.fillMaxWidth()
            )
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        }
    }
} 