package mamani.luna.notkert.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import mamani.luna.notkert.ui.screens.splash.SplashScreen
import mamani.luna.notkert.ui.screens.login.LoginScreen
import mamani.luna.notkert.ui.screens.register.RegisterScreen
import mamani.luna.notkert.ui.screens.catalog.CatalogScreen
import mamani.luna.notkert.ui.screens.detail.ProductDetailScreen
import mamani.luna.notkert.ui.screens.addproduct.AddProductScreen
import mamani.luna.notkert.ui.screens.cart.CartScreen
import mamani.luna.notkert.ui.screens.settings.SettingsScreen
import mamani.luna.notkert.ui.screens.animations.AnimationsShowcaseScreen
import mamani.luna.notkert.viewmodel.AuthViewModel
import mamani.luna.notkert.viewmodel.ProductViewModel
import mamani.luna.notkert.viewmodel.CartViewModel
import mamani.luna.notkert.ui.screens.welcome.WelcomeScreen

@Composable
fun NotkertNavGraph(navController: NavHostController) {
    val cartViewModel: CartViewModel = viewModel()
    NavHost(
        navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onLogin = { navController.navigate(Screen.Login.route) { popUpTo(Screen.Welcome.route) { inclusive = true } } },
                onRegister = { navController.navigate(Screen.Register.route) { popUpTo(Screen.Welcome.route) { inclusive = true } } }
            )
        }
        composable(Screen.Login.route) {
            val authViewModel: AuthViewModel = viewModel()
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    if (FirebaseAuth.getInstance().currentUser != null) {
                        navController.navigate(Screen.Catalog.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route) {
                        popUpTo(Screen.Login.route) { inclusive = false }
                    }
                }
            )
        }
        composable(Screen.Register.route) {
            val authViewModel: AuthViewModel = viewModel()
            RegisterScreen(
                viewModel = authViewModel,
                onRegisterSuccess = {
                    if (FirebaseAuth.getInstance().currentUser != null) {
                        navController.navigate(Screen.Catalog.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
                        }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.Catalog.route) {
            val user = FirebaseAuth.getInstance().currentUser
            if (user == null) {
                LaunchedEffect(Unit) {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Catalog.route) { inclusive = true }
                    }
                }
            } else {
                val productViewModel: ProductViewModel = viewModel()
                CatalogScreen(
                    viewModel = productViewModel,
                    onAddProduct = { navController.navigate(Screen.AddProduct.route) },
                    onProductClick = { id -> navController.navigate(Screen.ProductDetail.createRoute(id)) },
                    onCartClick = { navController.navigate(Screen.Cart.route) },
                    onSettingsClick = { navController.navigate(Screen.Settings.route) },
                    cartViewModel = cartViewModel
                )
            }
        }
        composable(Screen.ProductDetail.route) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(navController, productId, cartViewModel = cartViewModel)
        }
        composable(Screen.AddProduct.route) {
            val productViewModel: ProductViewModel = viewModel()
            AddProductScreen(viewModel = productViewModel, onProductAdded = {
                navController.popBackStack()
            })
        }
        composable(Screen.Cart.route) {
            CartScreen(viewModel = cartViewModel, onCheckout = {
                navController.popBackStack()
            })
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController)
        }
        composable(Screen.Animations.route) {
            AnimationsShowcaseScreen(navController)
        }
    }
}