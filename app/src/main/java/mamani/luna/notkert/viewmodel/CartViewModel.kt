package mamani.luna.notkert.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import mamani.luna.notkert.data.model.CartItem
import mamani.luna.notkert.data.model.Product
import mamani.luna.notkert.data.repository.CartRepository
import com.google.firebase.firestore.FirebaseFirestore

class CartViewModel(private val repo: CartRepository = CartRepository()) : ViewModel() {
    var cart = mutableStateOf<List<CartItem>>(emptyList())

    fun loadCart() {
        cart.value = repo.getCart()
    }

    fun addToCart(product: Product) {
        repo.addToCart(product)
        loadCart()
    }

    fun removeFromCart(productId: String) {
        repo.removeFromCart(productId)
        loadCart()
    }

    fun clearCart() {
        repo.clearCart()
        loadCart()
    }

    fun updateProductStock(productId: String, newStock: Int, onComplete: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        if (newStock > 0) {
            db.collection("productos").document(productId)
                .update("stock", newStock)
                .addOnSuccessListener { onComplete(true) }
                .addOnFailureListener { onComplete(false) }
        } else {
            db.collection("productos").document(productId)
                .delete()
                .addOnSuccessListener { onComplete(true) }
                .addOnFailureListener { onComplete(false) }
        }
    }

    fun updateCartItemQuantity(productId: String, newQuantity: Int) {
        repo.updateCartItemQuantity(productId, newQuantity)
        loadCart()
    }
} 