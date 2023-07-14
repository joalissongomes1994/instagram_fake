package joalissongomes.dev.instagram.home

import joalissongomes.dev.instagram.common.base.BasePresenter
import joalissongomes.dev.instagram.common.base.BaseView
import joalissongomes.dev.instagram.common.model.Post

interface Home {

    interface Presenter : BasePresenter {
        fun fetchFeed()
        fun clear()
        fun logout()
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayRequestFailure(message: String)
        fun displayEmptyPost()
        fun displayFullPosts(posts: List<Post>)
    }
}