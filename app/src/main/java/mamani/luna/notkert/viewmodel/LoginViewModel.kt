package mamani.luna.notkert.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    var username = mutableStateOf("")
    var password = mutableStateOf("")
    var error = mutableStateOf<String?>(null)
    var isLoggedIn = mutableStateOf(false)

    fun login() {
        if (username.value == "admin" && password.value == "1234") {
            isLoggedIn.value = true
            error.value = null
        } else {
            error.value = "Usuario o contraseña incorrectos"
        }
    }
}