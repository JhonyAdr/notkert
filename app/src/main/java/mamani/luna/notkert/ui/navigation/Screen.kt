package mamani.luna.notkert.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")
    object Catalog : Screen("catalog")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    object AddProduct : Screen("add_product")
    object Cart : Screen("cart")
    object Settings : Screen("settings")
    object Animations : Screen("animations")
} 