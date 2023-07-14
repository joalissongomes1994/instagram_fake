package joalissongomes.dev.instagram.profile.data

import joalissongomes.dev.instagram.common.base.Cache
import joalissongomes.dev.instagram.common.model.Post
import joalissongomes.dev.instagram.common.model.User

class ProfileDataSourceFactory(
    private val profileCache: Cache<Pair<User, Boolean?>>,
    private val postsCache: Cache<List<Post>>,
) {

    fun createLocalDataSource(): ProfileDataSource {
        return ProfileLocalDataSource(profileCache, postsCache)
    }

    fun createRemoteDataSource(): ProfileDataSource {
        return FireProfileDataSource()
    }

    fun createFromUser(uuid: String?): ProfileDataSource {
        return when {
            uuid != null -> createRemoteDataSource()
            profileCache.isCached() -> ProfileLocalDataSource(profileCache, postsCache)
            else -> createRemoteDataSource()
        }
    }

    fun createFromPosts(uuid: String?): ProfileDataSource {
        return when {
            uuid != null -> createRemoteDataSource()
            postsCache.isCached() -> ProfileLocalDataSource(profileCache, postsCache)
            else -> createRemoteDataSource()
        }
    }
}