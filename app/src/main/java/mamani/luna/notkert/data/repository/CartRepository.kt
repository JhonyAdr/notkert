package mamani.luna.notkert.data.repository

import mamani.luna.notkert.data.model.CartItem
import mamani.luna.notkert.data.model.Product

class CartRepository {
    private val cartItems = mutableListOf<CartItem>()

    fun getCart(): List<CartItem> = cartItems.toList()

    fun addToCart(product: Product) {
        val existing = cartItems.find { it.product.id == product.id }
        if (existing != null) {
            val updated = existing.copy(cantidad = existing.cantidad + 1)
            cartItems[cartItems.indexOf(existing)] = updated
        } else {
            cartItems.add(CartItem(product))
        }
    }

    fun removeFromCart(productId: String) {
        cartItems.removeAll { it.product.id == productId }
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun updateCartItemQuantity(productId: String, newQuantity: Int) {
        val item = cartItems.find { it.product.id == productId }
        if (item != null && newQuantity in 1..item.product.stock) {
            val updated = item.copy(cantidad = newQuantity)
            cartItems[cartItems.indexOf(item)] = updated
        }
    }
} 