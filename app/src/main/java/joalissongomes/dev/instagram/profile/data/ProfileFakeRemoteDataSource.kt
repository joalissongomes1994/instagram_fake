package joalissongomes.dev.instagram.profile.data

import android.os.Handler
import android.os.Looper
import joalissongomes.dev.instagram.common.base.RequestCallback
import joalissongomes.dev.instagram.common.model.Database
import joalissongomes.dev.instagram.common.model.Post
import joalissongomes.dev.instagram.common.model.User

class ProfileFakeRemoteDataSource : ProfileDataSource {

    override fun fetchUserProfile(
        userUUID: String,
        callback: RequestCallback<Pair<User, Boolean?>>
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull { userUUID == it.uuid }

            if (userAuth != null) {

                if (userAuth == Database.sessionAuth) {
//                    callback.onSuccess(Pair(userAuth, null))
                } else {
                    val followings = Database.followers[Database.sessionAuth!!.uuid]

                    val destUser = followings?.firstOrNull { it == userUUID }

                    val isFollowing = destUser != null

//                    callback.onSuccess(Pair(userAuth, isFollowing))
                }

            } else {
                callback.onFailure("usuário não encontrado")
            }

            callback.onComplete()
        }, 2000)
    }

    override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val posts = Database.posts[userUUID]

            callback.onSuccess(posts?.toList() ?: emptyList())
            callback.onComplete()
        }, 2000)
    }

    override fun followUser(
        userUUID: String,
        isFollow: Boolean,
        callback: RequestCallback<Boolean>
    ) {
        Handler(Looper.getMainLooper()).postDelayed({

            var follower = Database.followers[Database.sessionAuth!!.uuid]

            if (follower == null) {
                follower = mutableSetOf()
                Database.followers[Database.sessionAuth!!.uuid] = follower
            }

            if (isFollow) {
                follower.add(userUUID)
            } else {
                follower.remove(userUUID)
            }

            callback.onSuccess(true)
            callback.onComplete()
        }, 500)
    }
}