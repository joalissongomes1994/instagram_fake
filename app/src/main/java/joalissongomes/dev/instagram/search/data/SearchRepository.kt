package joalissongomes.dev.instagram.search.data

import joalissongomes.dev.instagram.common.base.RequestCallback
import joalissongomes.dev.instagram.common.model.User
import joalissongomes.dev.instagram.common.model.UserAuth

class SearchRepository(private val dataSource: SearchDataSource) {

    fun fetchUsers(name: String, callback: RequestCallback<List<User>>) {
        dataSource.fetchUsers(name, callback)
    }
}