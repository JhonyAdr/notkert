package mamani.luna.notkert.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import mamani.luna.notkert.data.repository.AuthRepository

class AuthViewModel(private val repo: AuthRepository = AuthRepository()) : ViewModel() {
    var isLoggedIn = mutableStateOf(repo.getCurrentUser() != null)
    var error = mutableStateOf<String?>(null)

    fun login(email: String, password: String) {
        repo.login(email, password) { success, message ->
            isLoggedIn.value = success
            error.value = if (success) null else message
        }
    }

    fun register(nombre: String, email: String, password: String) {
        repo.register(nombre, email, password) { success, message ->
            isLoggedIn.value = success
            error.value = if (success) null else message
        }
    }

    fun logout() {
        repo.logout()
        isLoggedIn.value = false
    }
} 