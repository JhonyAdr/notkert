package mamani.luna.notkert.ui.screens.animations

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AnimationsShowcaseScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Demo de Animaciones") },
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "Volver") } }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { InfiniteTransitionExample() }
            item { AnimatedContentExample() }
            item { CrossfadeExample() }
            item { AnimateContentSizeExample("Haz clic para ver más... Este contenedor es reutilizable y muestra cómo el tamaño puede animarse suavemente.") }
        }
    }
}

@Composable
fun AnimationCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
fun InfiniteTransitionExample() {
    AnimationCard("1. InfiniteTransition") {
        // Ejemplo simple: contador animado
        var count by remember { mutableStateOf(0) }
        Button(onClick = { count++ }) {
            Text("Contar: $count")
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentExample() {
    AnimationCard("2. AnimatedContent") {
        var expanded by remember { mutableStateOf(false) }
        AnimatedContent(targetState = expanded) { targetExpanded ->
            if (targetExpanded) {
                Text("¡Contenido expandido!", modifier = Modifier.clickable { expanded = false })
            } else {
                Text("Haz clic para expandir", modifier = Modifier.clickable { expanded = true })
            }
        }
    }
}

@Composable
fun CrossfadeExample() {
    AnimationCard("3. Crossfade") {
        var selected by remember { mutableStateOf(true) }
        Crossfade(targetState = selected, animationSpec = tween(500)) { isSelected ->
            if (isSelected) {
                Text("Vista A", modifier = Modifier.clickable { selected = false })
            } else {
                Text("Vista B", modifier = Modifier.clickable { selected = true })
            }
        }
    }
}

@Composable
fun AnimateContentSizeExample(text: String) {
    AnimationCard("4. animateContentSize") {
        var isExpanded by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(animationSpec = tween(durationMillis = 300))
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { isExpanded = !isExpanded }
                .padding(16.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
} 