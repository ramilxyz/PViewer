package xyz.ramil.pviewer.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import xyz.ramil.pviewer.model.PostModel

interface Api {

    @GET("feed.php")
    suspend fun getFeed(
    ): List<PostModel>

    @GET("story.php")
    suspend fun getStory(
        @Query("id") post: Int
    ): PostModel
}