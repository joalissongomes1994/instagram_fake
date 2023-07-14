package joalissongomes.dev.instagram.add.data

import android.net.Uri
import joalissongomes.dev.instagram.common.base.RequestCallback
import joalissongomes.dev.instagram.common.model.UserAuth
import java.lang.UnsupportedOperationException
import java.util.*

interface AddDataSource {

    fun createPost(
        userUUID: String,
        uri: Uri,
        caption: String,
        callback: RequestCallback<Boolean>
    ) {
        throw UnsupportedOperationException()
    }

    fun fetchSession(): String {
        throw UnsupportedOperationException()
    }
}