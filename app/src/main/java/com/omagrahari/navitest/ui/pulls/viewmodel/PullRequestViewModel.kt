package com.omagrahari.navitest.ui.pulls.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omagrahari.navitest.data.repository.NaviRepository
import com.omagrahari.navitest.model.PullResponse
import com.omagrahari.navitest.model.common.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PullRequestViewModel @Inject constructor(
    private val repository: NaviRepository
) : ViewModel() {

    private val _pullLiveData = MutableLiveData<Response<List<PullResponse>>>()
    val pullLiveData: LiveData<Response<List<PullResponse>>>
        get() = _pullLiveData

    var page: Int = 1

    fun getClosedPullRequest(
        userName: String,
        repoName: String
    ) {
        viewModelScope.launch {
            _pullLiveData.postValue(Response.loading())
            val result = repository.getClosedPullRequest(userName, repoName, page)
            _pullLiveData.postValue(result)
        }
    }

    fun updatePage() {
        page += 1
    }

    fun refreshPage() {
        page = 1
    }

}