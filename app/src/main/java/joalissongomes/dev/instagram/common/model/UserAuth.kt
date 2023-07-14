package joalissongomes.dev.instagram.common.model

import android.net.Uri

data class UserAuth(
    val uuid: String,
    val name: String,
    val email: String,
    val photoUri: Uri?,
    val password: String,
    val postCount: Int = 0,
    var followingCount: Int = 0,
    val followersCount: Int = 0,
)
