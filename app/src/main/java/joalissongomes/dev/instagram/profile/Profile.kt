package joalissongomes.dev.instagram.profile

import joalissongomes.dev.instagram.common.base.BasePresenter
import joalissongomes.dev.instagram.common.base.BaseView
import joalissongomes.dev.instagram.common.model.Post
import joalissongomes.dev.instagram.common.model.User

interface Profile {

    interface Presenter : BasePresenter {
        fun fetchUserProfile(uuid: String?)
        fun fetchUserPosts(uuid: String?)
        fun followUser(uuid: String, follow: Boolean)
        fun clear()
    }


    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayUserProfile(user: Pair<User, Boolean?>)
        fun displayRequestFailure(message: String)
        fun displayEmptyPosts()
        fun displayFullPosts(posts: List<Post>)
        fun followUpdated()
    }
}