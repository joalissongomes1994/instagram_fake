package joalissongomes.dev.instagram.login

import androidx.annotation.StringRes
import joalissongomes.dev.instagram.common.base.BasePresenter
import joalissongomes.dev.instagram.common.base.BaseView
import joalissongomes.dev.instagram.common.model.UserAuth

interface Login {

    // layer Presenter
    interface Presenter : BasePresenter {
        fun login(email: String, password: String)
    }

    // layer View
    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayEmailFailure(@StringRes emailError: Int?)
        fun displayPasswordFailure(@StringRes passwordError: Int?)
        fun onUserAuthenticated()
        fun onUserUnauthorized(message: String)
    }
}