package joalissongomes.dev.instagram.post.data

class PostRepository(private val dataSource: PostDataSource) {

    suspend fun fetchPicture() = dataSource.fetchPictures()

}