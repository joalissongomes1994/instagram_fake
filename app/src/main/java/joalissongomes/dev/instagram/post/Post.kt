package joalissongomes.dev.instagram.post

import android.net.Uri
import joalissongomes.dev.instagram.common.base.BasePresenter
import joalissongomes.dev.instagram.common.base.BaseView

interface Post {

    interface Presenter : BasePresenter {
        fun selectUri(uri: Uri)
        fun getSelectedUri(): Uri?
        fun fetchPictures()
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayFullPictures(pictures: List<Uri>)
        fun displayEmptyPicture()
        fun displayRequestFailure(message: String)
    }
}