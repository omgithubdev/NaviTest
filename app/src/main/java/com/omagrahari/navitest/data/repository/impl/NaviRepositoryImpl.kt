package com.omagrahari.navitest.data.repository.impl

import com.omagrahari.navitest.data.network.ApiService
import com.omagrahari.navitest.data.network.safeApiCall
import com.omagrahari.navitest.data.repository.NaviRepository
import com.omagrahari.navitest.model.PullResponse
import com.omagrahari.navitest.model.common.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NaviRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : NaviRepository {

    override suspend fun getClosedPullRequest(
        userName: String,
        repoName: String,
        page: Int
    ): Response<List<PullResponse>> {
        return withContext(Dispatchers.IO) {
            return@withContext safeApiCall {
                apiService.getClosedPullRequests(
                    userName,
                    repoName,
                    page
                )
            }
        }
    }

}