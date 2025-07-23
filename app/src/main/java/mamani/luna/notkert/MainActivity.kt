package mamani.luna.notkert

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import mamani.luna.notkert.ui.navigation.NotkertNavGraph
import mamani.luna.notkert.ui.theme.NotkertTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate called")
        FirebaseAuth.getInstance().signOut() // Forzar logout al iniciar la app
        setContent {
            NotkertTheme {
                val navController = rememberNavController()
                NotkertNavGraph(navController)
            }
        }
    }
}
