package joalissongomes.dev.instagram.register

import androidx.annotation.StringRes
import joalissongomes.dev.instagram.common.base.BasePresenter
import joalissongomes.dev.instagram.common.base.BaseView

interface RegisterNameAndPassword {

    interface Presenter : BasePresenter {
        fun create(email: String, name: String, password: String, confirmPassword: String)
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayNameFailure(@StringRes nameError: Int ?)
        fun displayPasswordFailure(@StringRes passwordError: Int ?)
        fun displayConfirmPasswordFailure(@StringRes confirmPasswordError: Int ?)
        fun onCreateFailure(message: String)
        fun onCreateSuccess(name: String)
    }
}