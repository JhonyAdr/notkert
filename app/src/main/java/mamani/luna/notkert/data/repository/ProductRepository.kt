 package mamani.luna.notkert.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import mamani.luna.notkert.data.model.Product

class ProductRepository {
    private val db = FirebaseFirestore.getInstance()

    fun getProducts(onResult: (List<Product>) -> Unit) {
        db.collection("productos")
            .get()
            .addOnSuccessListener { result ->
                val products = result.map { it.toObject(Product::class.java).copy(id = it.id) }
                onResult(products)
            }
    }

    fun getProductsByUser(userId: String, onResult: (List<Product>) -> Unit) {
        db.collection("productos")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                val products = result.map { it.toObject(Product::class.java).copy(id = it.id) }
                onResult(products)
            }
    }

    fun addProduct(product: Product, onResult: (Boolean, String?) -> Unit) {
        db.collection("productos")
            .add(product)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { e -> onResult(false, e.message) }
    }
}
