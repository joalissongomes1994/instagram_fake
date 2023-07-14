package joalissongomes.dev.instagram.splash.data

import android.os.Handler
import android.os.Looper
import joalissongomes.dev.instagram.common.model.Database

class FakeLocalDataSource : SplashDataSource {

    override fun session(callback: SplashCallback) {

        when (Database.sessionAuth) {
            null -> {
                callback.onFailure()
            }
            else -> {
                callback.onSuccess()
            }
        }
    }
}