package joalissongomes.dev.instagram.search.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import joalissongomes.dev.instagram.common.base.RequestCallback
import joalissongomes.dev.instagram.common.model.User

class FireSearchDataSource : SearchDataSource {

    override fun fetchUsers(name: String, callback: RequestCallback<List<User>>) {
        FirebaseFirestore.getInstance()
            .collection("/users")
            .whereGreaterThanOrEqualTo("name", name)
            .whereLessThanOrEqualTo("name", name + "\uf8ff")
            .get()
            .addOnSuccessListener { res ->
                val users = mutableListOf<User>()
                val documents = res.documents

                for (document in documents) {
                    val user = document.toObject(User::class.java)

                    if (user != null && user.uuid != FirebaseAuth.getInstance().uid) {
                        users.add(user)
                    }
                }

                callback.onSuccess(users)
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Falha ao buscar usuário")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }
}