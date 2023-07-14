package joalissongomes.dev.instagram.home.data

import joalissongomes.dev.instagram.common.base.Cache
import joalissongomes.dev.instagram.common.model.Post

class HomeDataSourceFactory(
    private val feedCache: Cache<List<Post>>
) {

    fun createLocalDataSource(): HomeDataSource {
        return HomeLocalDataSource(feedCache)
    }

    fun createRemoteDataSource(): HomeDataSource {
        return FireHomeDataSource()
    }

    fun createFromFeed(): HomeDataSource {
        if (feedCache.isCached()) {
            return HomeLocalDataSource(feedCache)
        }

        return FireHomeDataSource()
    }
}