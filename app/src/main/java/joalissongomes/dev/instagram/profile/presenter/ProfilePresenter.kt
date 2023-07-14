package joalissongomes.dev.instagram.profile.presenter

import joalissongomes.dev.instagram.common.base.RequestCallback
import joalissongomes.dev.instagram.common.model.Post
import joalissongomes.dev.instagram.common.model.User
import joalissongomes.dev.instagram.profile.Profile
import joalissongomes.dev.instagram.profile.data.ProfileRepository

class ProfilePresenter(
    private var view: Profile.View?,
    private val repository: ProfileRepository
) : Profile.Presenter {


    override fun fetchUserProfile(uuid: String?) {
        view?.showProgress(true)
        repository.fetchUserProfile(uuid, object : RequestCallback<Pair<User, Boolean?>> {
            override fun onSuccess(data: Pair<User, Boolean?>) {
                view?.displayUserProfile(data)
            }

            override fun onFailure(message: String) {
                view?.displayRequestFailure(message)
            }

            override fun onComplete() {
            }
        })
    }

    override fun fetchUserPosts(uuid: String?) {
        repository.fetchUserPosts(uuid, object : RequestCallback<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                if (data.isEmpty()) {
                    view?.displayEmptyPosts()
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

    override fun followUser(uuid: String, follow: Boolean) {
        repository.followUser(uuid, follow, object : RequestCallback<Boolean> {
            override fun onSuccess(data: Boolean) {
                fetchUserProfile(uuid)

                if(data) {
                    view?.followUpdated()
                }
            }

            override fun onFailure(message: String) {}

            override fun onComplete() {}
        })
    }

    override fun clear() {
        repository.clearCache()
    }

    override fun onDestroy() {
        view = null
    }

}