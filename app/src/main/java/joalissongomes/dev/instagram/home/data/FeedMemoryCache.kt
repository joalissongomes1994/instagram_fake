package joalissongomes.dev.instagram.home.data

import joalissongomes.dev.instagram.common.base.Cache
import joalissongomes.dev.instagram.common.model.Post

object FeedMemoryCache : Cache<List<Post>> {

    private var posts: List<Post>? = null

    override fun isCached(): Boolean {
        return posts != null
    }

    override fun get(key: String): List<Post>? {
        return posts
    }

    override fun put(data: List<Post>?) {
        posts = data
    }


}