package joalissongomes.dev.instagram.home.presenter

import joalissongomes.dev.instagram.common.base.RequestCallback
import joalissongomes.dev.instagram.common.model.Post
import joalissongomes.dev.instagram.home.Home
import joalissongomes.dev.instagram.home.data.HomeRepository

class HomePresenter(
    private var view: Home.View?,
    private val repository: HomeRepository
) : Home.Presenter {

    override fun clear() {
        repository.clearCache()
    }

    override fun fetchFeed() {
        view?.showProgress(true)

        repository.fetchFeed(object : RequestCallback<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                if (data.isEmpty()) {
                    view?.displayEmptyPost()
                } else {
                    view?.displayFullPosts(data)
                }
            }

            override fun onFailure(message: String) {
                view?.displayRequestFailure(message)
            }

            override fun onComplete() {
                view?.showProgress(false)
            }
        })
    }

    override fun logout() {
        repository.logout()
    }

    override fun onDestroy() {
        view = null
    }
}