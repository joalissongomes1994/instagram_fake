package joalissongomes.dev.instagram.profile.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import joalissongomes.dev.instagram.common.base.RequestCallback
import joalissongomes.dev.instagram.common.model.Post
import joalissongomes.dev.instagram.common.model.User
import java.lang.RuntimeException

class FireProfileDataSource : ProfileDataSource {

    override fun fetchUserProfile(
        userUUID: String,
        callback: RequestCallback<Pair<User, Boolean?>>
    ) {

        val uid = FirebaseAuth.getInstance().uid ?: throw RuntimeException("usuário não logado")

        FirebaseFirestore.getInstance()
            .collection("/users")
            .document(userUUID)
            .get()
            .addOnSuccessListener { res ->
                val user = res.toObject(User::class.java)

                when (user) {
                    null -> callback.onFailure("Erro ao converter usuário")
                    else -> {
                        if (user.uuid == uid) {
                            callback.onSuccess(Pair(user, null))
                        } else {

                            FirebaseFirestore.getInstance()
                                .collection("/followers")
                                .document(userUUID)
                                .get()
                                .addOnSuccessListener { response ->

                                    if (!response.exists()) {
                                        callback.onSuccess(Pair(user, false))
                                    } else {
                                        val list = response.get("followers") as List<String>
                                        callback.onSuccess(Pair(user, list.contains(uid)))
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    callback.onFailure(
                                        exception.message ?: "Falha ao buscar seguidores"
                                    )
                                }
                                .addOnCompleteListener {
                                    callback.onComplete()
                                }
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Falha ao buscar usuário")
            }
    }

    override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
        FirebaseFirestore.getInstance()
            .collection("posts")
            .document(userUUID)
            .collection("posts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { res ->
                val posts = mutableListOf<Post>()
                val documents = res.documents

                for (document in documents) {
                    val post = document.toObject(Post::class.java)

                    post?.let {
                        posts.add(it)
                    }
                }

                callback.onSuccess(posts)
            }
            .addOnFailureListener { exception ->
                callback.onFailure(
                    exception.message ?: "Falha ao busar postagens"
                )
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    override fun followUser(
        userUUID: String,
        isFollow: Boolean,
        callback: RequestCallback<Boolean>
    ) {
        val uid = FirebaseAuth.getInstance().uid ?: throw RuntimeException("Usuário não logado")

        FirebaseFirestore.getInstance()
            .collection("/followers")
            .document(userUUID)
            .update(
                "followers",
                if (isFollow) FieldValue.arrayUnion(uid)
                else FieldValue.arrayRemove(uid)

            )
            .addOnSuccessListener { res ->
                followingCounter(uid, isFollow)
                followersCounter(userUUID, callback)
                updateFeed(userUUID, isFollow)
            }
            .addOnFailureListener { exception ->
                val err = exception as? FirebaseFirestoreException

                if (err?.code == FirebaseFirestoreException.Code.NOT_FOUND) {
                    FirebaseFirestore.getInstance()
                        .collection("/followers")
                        .document(userUUID)
                        .set(
                            hashMapOf(
                                "followers" to listOf(uid)
                            )
                        )
                        .addOnSuccessListener {
                            followingCounter(uid, isFollow)
                            followersCounter(userUUID, callback)
                            updateFeed(userUUID, isFollow)
                        }
                        .addOnFailureListener { error ->
                            callback.onFailure(error.message ?: "Falha ao criar seguidor")
                        }
                }

                callback.onFailure(exception.message ?: "Falha ao atualizar seguidor")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    private fun followingCounter(uid: String, isFollow: Boolean) {
        val meRef = FirebaseFirestore.getInstance()
            .collection("/users")
            .document(uid)

        if (isFollow) meRef.update("following", FieldValue.increment(1))
        else meRef.update("following", FieldValue.increment(-1))
    }

    private fun followersCounter(uid: String, callback: RequestCallback<Boolean>) {
        val meRef = FirebaseFirestore.getInstance()
            .collection("/users")
            .document(uid)

        FirebaseFirestore.getInstance()
            .collection("/followers")
            .document(uid)
            .get()
            .addOnSuccessListener { res ->
                if (res.exists()) {
                    val list = res.get("followers") as List<String>
                    meRef.update("followers", list.size)
                }
                callback.onSuccess(true)
            }
    }

    private fun updateFeed(uid: String, isFollow: Boolean) {

        if (!isFollow) {

            FirebaseFirestore.getInstance()
                .collection("/feeds")
                .document(FirebaseAuth.getInstance().uid!!)
                .collection("posts")
                .whereEqualTo("publisher.uuid", uid)
                .get()
                .addOnSuccessListener { res ->
                    val documents = res.documents
                    for (document in documents) {
                        document.reference.delete()
                    }
                }

        } else {

            FirebaseFirestore.getInstance()
                .collection("/posts")
                .document(uid)
                .collection("posts")
                .get()
                .addOnSuccessListener { res ->
                    val posts = res.toObjects(Post::class.java)

                    posts.lastOrNull()?.let {
                        FirebaseFirestore.getInstance()
                            .collection("/feeds")
                            .document(FirebaseAuth.getInstance().uid!!)
                            .collection("posts")
                            .document(it.uuid!!)
                            .set(it)
                    }
                }
        }
    }
}