package joalissongomes.dev.instagram.register.presenter

import android.net.Uri
import joalissongomes.dev.instagram.register.RegisterPhoto
import joalissongomes.dev.instagram.register.data.RegisterCallback
import joalissongomes.dev.instagram.register.data.RegisterRepository

class RegisterPhotoPresenter(
    private var view: RegisterPhoto.View?,
    private val repository: RegisterRepository
) : RegisterPhoto.Presenter {

    override fun updateUser(photoUri: Uri) {

        view?.showProgress(true)

        repository.update(photoUri, object : RegisterCallback {
            override fun onSuccess() {
                view?.onUpdateSuccess()
            }

            override fun onFailure(message: String) {
                view?.onUpdateFailure(message)
            }

            override fun onComplete() {
                view?.showProgress(false)
            }
        })
    }

    override fun onDestroy() {
        view = null
    }
}