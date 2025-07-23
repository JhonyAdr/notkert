package mamani.luna.notkert.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import mamani.luna.notkert.data.model.Product
import mamani.luna.notkert.data.repository.ProductRepository

class ProductViewModel(private val repo: ProductRepository = ProductRepository()) : ViewModel() {
    var products = mutableStateOf<List<Product>>(emptyList())
    var myProducts = mutableStateOf<List<Product>>(emptyList())
    var error = mutableStateOf<String?>(null)
    var addSuccess = mutableStateOf(false)

    fun loadProducts() {
        repo.getProducts { list ->
            products.value = list
        }
    }

    fun loadProductsByUser(userId: String) {
        repo.getProductsByUser(userId) { list ->
            myProducts.value = list
        }
    }

    fun addProduct(product: Product) {
        repo.addProduct(product) { success, message ->
            addSuccess.value = success
            error.value = message
        }
    }
} 