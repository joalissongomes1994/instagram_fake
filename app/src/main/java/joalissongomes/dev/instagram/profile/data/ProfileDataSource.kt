package joalissongomes.dev.instagram.profile.data

import joalissongomes.dev.instagram.common.base.RequestCallback
import joalissongomes.dev.instagram.common.model.Post
import joalissongomes.dev.instagram.common.model.User

interface ProfileDataSource {

    fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<User, Boolean?>>)

    fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>)

    fun followUser(userUUID: String, isFollow: Boolean, callback: RequestCallback<Boolean>) {
        throw UnsupportedOperationException()
    }

    fun fetchSession(): String {
        throw UnsupportedOperationException()
    }

    fun putUser(response: Pair<User, Boolean?>?) {
        throw UnsupportedOperationException()
    }

    fun putPosts(response: List<Post>?) {
        throw UnsupportedOperationException()
    }
}