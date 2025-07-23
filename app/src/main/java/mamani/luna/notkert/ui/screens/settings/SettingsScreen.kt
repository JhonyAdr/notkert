package mamani.luna.notkert.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    var darkTheme by remember { mutableStateOf(false) }
    val user = FirebaseAuth.getInstance().currentUser
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajustes") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Apariencia", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Icon(Icons.Default.Settings, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                Spacer(Modifier.width(8.dp))
                Text("Tema oscuro", style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.weight(1f))
                Switch(checked = darkTheme, onCheckedChange = { darkTheme = it })
            }
            Divider()
            Text("Usuario", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            SettingsItem(
                icon = Icons.Default.Info,
                title = user?.email ?: "No autenticado",
                subtitle = "ID: ${user?.uid ?: "-"}"
            )
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión")
            }
            Divider()
            Text("Acerca de", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            SettingsItem(
                icon = Icons.Default.Info,
                title = "Versión de la App",
                subtitle = "1.0.0 (Build 1)"
            )
        }
    }
}

@Composable
fun SettingsItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.padding(end = 16.dp),
            tint = MaterialTheme.colorScheme.secondary
        )
        Column {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Text(text = subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        }
    }
} 