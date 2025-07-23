package mamani.luna.notkert.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import mamani.luna.notkert.data.model.User

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful, task.exception?.message)
            }
    }

    fun register(nombre: String, email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userData = User(id = user?.uid ?: "", nombre = nombre, email = email)
                    db.collection("usuarios").document(user?.uid ?: "").set(userData)
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun logout() {
        auth.signOut()
    }
} 