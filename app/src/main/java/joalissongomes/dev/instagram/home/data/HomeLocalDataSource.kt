package joalissongomes.dev.instagram.home.data

import com.google.firebase.auth.FirebaseAuth
import joalissongomes.dev.instagram.common.base.Cache
import joalissongomes.dev.instagram.common.base.RequestCallback
import joalissongomes.dev.instagram.common.model.Database
import joalissongomes.dev.instagram.common.model.Post
import joalissongomes.dev.instagram.common.model.UserAuth
import java.lang.RuntimeException

class HomeLocalDataSource(
    private val feedCache: Cache<List<Post>>
) : HomeDataSource {

    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
        val posts = feedCache.get(userUUID)

        if (posts != null) {
            callback.onSuccess(posts)
        } else {
            callback.onFailure("nenhuma postagem encontrada")
        }

        callback.onComplete()
    }

    override fun fetchSession(): String {
        return FirebaseAuth.getInstance().uid ?: throw RuntimeException("usuário não logado")
    }

    override fun putFeed(response: List<Post>?) {
        feedCache.put(response)
    }
}