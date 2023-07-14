package joalissongomes.dev.instagram.splash.presenter

import joalissongomes.dev.instagram.splash.Splash
import joalissongomes.dev.instagram.splash.data.SplashCallback
import joalissongomes.dev.instagram.splash.data.SplashRepository

class SplashPresenter(
    private var view: Splash.View?,
    private val repository: SplashRepository
): Splash.Presenter {

    override fun authenticated() {
        repository.session(object : SplashCallback {
            override fun onSuccess() {
                view?.goToMainScreen()
            }

            override fun onFailure() {
                view?.goToLoginScreen()
            }
        })
    }

    override fun onDestroy() {
        view = null
    }
}