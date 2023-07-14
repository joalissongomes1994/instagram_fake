package joalissongomes.dev.instagram.register

import android.net.Uri
import joalissongomes.dev.instagram.common.base.BasePresenter
import joalissongomes.dev.instagram.common.base.BaseView

interface RegisterPhoto {

    interface Presenter : BasePresenter {
        fun updateUser(photoUri: Uri)
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun onUpdateFailure(message: String)
        fun onUpdateSuccess()
    }
}