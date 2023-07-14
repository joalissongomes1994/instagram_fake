package joalissongomes.dev.instagram.home.data

import android.os.Handler
import android.os.Looper
import joalissongomes.dev.instagram.common.base.RequestCallback
import joalissongomes.dev.instagram.common.model.Database
import joalissongomes.dev.instagram.common.model.Post

class HomeFakeRemoteDataSource : HomeDataSource {

    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
        Handler(Looper.getMainLooper()).postDelayed({

            val feed = Database.feeds[userUUID]

            callback.onSuccess(feed?.toList() ?: emptyList())

            callback.onComplete()
        }, 2000)
    }
}