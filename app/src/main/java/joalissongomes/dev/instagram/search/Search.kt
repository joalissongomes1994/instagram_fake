package joalissongomes.dev.instagram.search

import joalissongomes.dev.instagram.common.base.BasePresenter
import joalissongomes.dev.instagram.common.base.BaseView
import joalissongomes.dev.instagram.common.model.User

interface Search {

    interface Presenter : BasePresenter {
        fun fetchUsers(name: String)
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayFullUsers(users: List<User>)
        fun displayEmptyUsers()
    }
}