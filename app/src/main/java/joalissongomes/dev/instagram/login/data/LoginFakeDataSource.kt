package joalissongomes.dev.instagram.login.data

import android.os.Handler
import android.os.Looper
import joalissongomes.dev.instagram.common.model.Database

class
LoginFakeDataSource : LoginDataSource {
    override fun login(email: String, password: String, callback: LoginCallback) {
        Handler(Looper.getMainLooper()).postDelayed({

            val userAuth = Database.usersAuth.firstOrNull { it.email == email }

            when {
                userAuth == null -> {
                    callback.onFailure("Usuário não encontrado!")
                }
                userAuth.password != password -> {
                    callback.onFailure("Senha está incorreta!")
                }
                else -> {
                    callback.onSuccess()
                    Database.sessionAuth = userAuth
                }
            }

            callback.onComplete()
        }, 2000)
    }
}