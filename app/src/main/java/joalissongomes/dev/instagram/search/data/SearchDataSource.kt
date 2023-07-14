package joalissongomes.dev.instagram.search.data

import joalissongomes.dev.instagram.common.base.RequestCallback
import joalissongomes.dev.instagram.common.model.User

interface SearchDataSource {

    fun fetchUsers(name: String, callback: RequestCallback<List<User>>)
}