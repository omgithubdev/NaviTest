package com.omagrahari.navitest.data.network

import com.omagrahari.navitest.model.PullResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("repos/{user_name}/{repo_name}/pulls?state=closed")
    suspend fun getClosedPullRequests(
        @Path("user_name") userName: String,
        @Path("repo_name") repoName: String,
        @Query("page") page: Int
    ): Response<List<PullResponse>>

}