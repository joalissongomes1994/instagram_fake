package joalissongomes.dev.instagram.search.data

import android.os.Handler
import android.os.Looper
import joalissongomes.dev.instagram.common.base.RequestCallback
import joalissongomes.dev.instagram.common.model.Database
import joalissongomes.dev.instagram.common.model.User

class SearchFakeDataSource : SearchDataSource {

    override fun fetchUsers(name: String, callback: RequestCallback<List<User>>) {

        Handler(Looper.getMainLooper()).postDelayed({

            val users =
                Database.usersAuth.filter {
                    it.name.lowercase()
                        .startsWith(name.lowercase()) && it.uuid != Database.sessionAuth!!.uuid
                }

//            callback.onSuccess(users.toList())

            callback.onComplete()

        }, 1000)
    }
}