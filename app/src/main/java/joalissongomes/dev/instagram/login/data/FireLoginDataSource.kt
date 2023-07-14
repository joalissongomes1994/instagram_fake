package joalissongomes.dev.instagram.login.data

import com.google.firebase.auth.FirebaseAuth

class FireLoginDataSource: LoginDataSource {

    override fun login(email: String, password: String, callback: LoginCallback) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->

                if(result.user != null) {
                    callback.onSuccess()
                } else {
                callback.onFailure("usuário não encontrado")
                }
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Error interno no servidor")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }
}