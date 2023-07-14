package joalissongomes.dev.instagram.common.model

import android.net.Uri
import java.io.File
import java.util.*

object Database {

    val usersAuth = mutableListOf<UserAuth>()
    val posts = hashMapOf<String, MutableSet<Post>>()
    val feeds = hashMapOf<String, MutableSet<Post>>()
    val followers = hashMapOf<String, MutableSet<String>>()

    var sessionAuth: UserAuth? = null

    init {
//        val userA = UserAuth(UUID.randomUUID().toString(), "UserA", "userA@gmail.com", null, "12345678")

//        val userB = UserAuth(
//            UUID.randomUUID().toString(), "UserB", "userB@gmail.com",
//            Uri.fromFile(
//                File("/storage/emulated/0/Android/media/joalissongomes.dev.instagram/Instagram/2023-06-22-11-54-43-145.jpg")
//            ), "87654321"
//        )
//
//
//        usersAuth.add(userA)
//        usersAuth.add(userB)
//
//        followers[userA.uuid] = hashSetOf()
//        posts[userA.uuid] = hashSetOf()
//        feeds[userA.uuid] = hashSetOf()
//
//        followers[userB.uuid] = hashSetOf()
//        posts[userB.uuid] = hashSetOf()
//        feeds[userB.uuid] = hashSetOf()
//
//        for (i in 0..30) {
//            val user = UserAuth(
//                UUID.randomUUID().toString(),
//                "User$i",
//                "user$1@gmail.com",
//                null,
//                "123123123",
//            )
//
//            usersAuth.add(user)
//        }
//
//        sessionAuth = usersAuth.first()

//        followers[sessionAuth!!.uuid]?.add(usersAuth[2].uuid)
    }
}