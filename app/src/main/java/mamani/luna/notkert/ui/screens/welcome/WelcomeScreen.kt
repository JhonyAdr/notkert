package mamani.luna.notkert.ui.screens.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mamani.luna.notkert.ui.components.PrimaryButton

@Composable
fun WelcomeScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Notkert",
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 48.sp),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(32.dp))
            PrimaryButton(
                text = "Iniciar sesión",
                onClick = onLogin,
                modifier = Modifier.fillMaxWidth(0.7f)
            )
            Spacer(Modifier.height(16.dp))
            PrimaryButton(
                text = "Registrarse",
                onClick = onRegister,
                modifier = Modifier.fillMaxWidth(0.7f)
            )
        }
    }
} 