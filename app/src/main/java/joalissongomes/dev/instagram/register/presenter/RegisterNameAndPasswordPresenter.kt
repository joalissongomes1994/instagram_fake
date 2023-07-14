package joalissongomes.dev.instagram.register.presenter

import joalissongomes.dev.instagram.R
import joalissongomes.dev.instagram.register.RegisterNameAndPassword
import joalissongomes.dev.instagram.register.data.RegisterCallback
import joalissongomes.dev.instagram.register.data.RegisterRepository

class RegisterNameAndPasswordPresenter(
    private var view: RegisterNameAndPassword.View?,
    private val repository: RegisterRepository
) : RegisterNameAndPassword.Presenter {

    override fun create(
        email: String,
        name: String,
        password: String,
        confirmPassword: String
    ) {
        val isNameValid = name.length > 3
        val isPasswordValid = password.length >= 8
        val isConfirmValid = password == confirmPassword

        when {
            !isNameValid -> {
                view?.displayNameFailure(R.string.invalid_name)
            }
            else -> {
                view?.displayNameFailure(null)
            }
        }

        when {
            !isPasswordValid -> {
                view?.displayPasswordFailure(R.string.invalid_password)
            }
            else -> {
                view?.displayPasswordFailure(null)
            }
        }

        when {
            !isConfirmValid -> {
                view?.displayConfirmPasswordFailure(R.string.password_not_equal)
            }
            else -> {
                view?.displayConfirmPasswordFailure(null)
            }
        }

        if (isNameValid && isPasswordValid && isConfirmValid) {

            view?.showProgress(true)

            repository.create(email, name, password, object : RegisterCallback {
                override fun onSuccess() {
                    view?.onCreateSuccess(name)
                }

                override fun onFailure(message: String) {
                    view?.onCreateFailure(message)
                }

                override fun onComplete() {
                    view?.showProgress(false)
                }

            })
        }
    }

    override fun onDestroy() {
        view = null
    }
}