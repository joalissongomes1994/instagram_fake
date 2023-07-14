package joalissongomes.dev.instagram.profile.data

import com.google.firebase.auth.FirebaseAuth
import joalissongomes.dev.instagram.common.base.Cache
import joalissongomes.dev.instagram.common.base.RequestCallback
import joalissongomes.dev.instagram.common.model.Database
import joalissongomes.dev.instagram.common.model.Post
import joalissongomes.dev.instagram.common.model.User
import joalissongomes.dev.instagram.common.model.UserAuth

class ProfileLocalDataSource(
    private val profileCache: Cache<Pair<User, Boolean?>>,
    private val postsCache: Cache<List<Post>>,
) : ProfileDataSource {

    override fun fetchUserProfile(
        userUUID: String,
        callback: RequestCallback<Pair<User, Boolean?>>
    ) {
        val userAuth = profileCache.get(userUUID)

        if (userAuth != null) {
            callback.onSuccess(userAuth)
        } else {
            callback.onFailure("usuário não encontrado")
        }

        callback.onComplete()
    }

    override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
        val posts = postsCache.get(userUUID)

        if (posts != null) {
            callback.onSuccess(posts)
        } else {
            callback.onFailure("Nenhum post encontrado")
        }

        callback.onComplete()
    }

    override fun fetchSession(): String {
        return FirebaseAuth.getInstance().uid ?: throw RuntimeException("usuário não logado")
    }

    override fun putUser(response: Pair<User, Boolean?>?) {
        profileCache.put(response)
    }


    override fun putPosts(response: List<Post>?) {
        postsCache.put(response)
    }
}