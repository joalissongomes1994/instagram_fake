package joalissongomes.dev.instagram.home.data

import joalissongomes.dev.instagram.common.base.RequestCallback
import joalissongomes.dev.instagram.common.model.Post

interface HomeDataSource {

    fun logout() {
        throw UnsupportedOperationException()
    }

    fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>)

    fun fetchSession(): String {
        throw UnsupportedOperationException()
    }

    fun putFeed(response: List<Post>?) {
        throw UnsupportedOperationException()
    }
}