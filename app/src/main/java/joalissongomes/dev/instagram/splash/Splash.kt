package joalissongomes.dev.instagram.splash

import joalissongomes.dev.instagram.common.base.BasePresenter
import joalissongomes.dev.instagram.common.base.BaseView

interface Splash {

    interface Presenter: BasePresenter {
        fun authenticated()
    }

    interface View: BaseView<Presenter> {
        fun goToMainScreen()
        fun goToLoginScreen()
    }
}