package joalissongomes.dev.instagram.register

import androidx.annotation.StringRes
import joalissongomes.dev.instagram.common.base.BasePresenter
import joalissongomes.dev.instagram.common.base.BaseView

interface RegisterEmail {

    interface Presenter : BasePresenter {
        fun create(email: String)
    }

    interface View : BaseView<Presenter> {
        fun goToNameAndPasswordScreen(email: String)
        fun displayEmailFailure(@StringRes emailError: Int?)
        fun onEmailFailure(message: String)
        fun showProgress(enabled: Boolean)
    }
}