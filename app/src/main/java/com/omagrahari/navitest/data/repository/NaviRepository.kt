package com.omagrahari.navitest.data.repository

import com.omagrahari.navitest.model.PullResponse
import com.omagrahari.navitest.model.common.Response

interface NaviRepository {

    suspend fun getClosedPullRequest(
        userName: String,
        repoName: String,
        page: Int
    ): Response<List<PullResponse>>

}