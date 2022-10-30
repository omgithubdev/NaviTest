package com.omagrahari.navitest.ui.pulls

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.omagrahari.navitest.R
import com.omagrahari.navitest.databinding.ActivityPullRequestBinding
import com.omagrahari.navitest.model.common.Status
import com.omagrahari.navitest.ui.base.BaseActivity
import com.omagrahari.navitest.ui.pulls.adapter.PullRequestAdapter
import com.omagrahari.navitest.ui.pulls.viewmodel.PullRequestViewModel
import com.omagrahari.navitest.util.makeGone
import com.omagrahari.navitest.util.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PullRequestActivity : BaseActivity<ActivityPullRequestBinding>() {

    @Inject
    lateinit var adapter: PullRequestAdapter

    private val viewModel: PullRequestViewModel by viewModels()

    private var loading = true
    private var pastVisibleItems = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0

    private lateinit var layoutManager: LinearLayoutManager

    override fun initialize() {
        super.initialize()

        binding.rvPullRequest.adapter = adapter
        layoutManager = binding.rvPullRequest.layoutManager as LinearLayoutManager

        updateViews()
        observeLiveData()
    }

    private fun updateViews() {
        val userName = getString(R.string.user_name)
        val repoName = getString(R.string.repo_name)

        binding.tvUser.text = getString(R.string.title_user_name, userName)

        binding.tvRepo.text = getString(R.string.title_repo_name, repoName)

        fetchData()
    }

    private fun fetchData() {
        viewModel.getClosedPullRequest(
            getString(R.string.user_name),
            getString(R.string.repo_name)
        )
    }

    private fun observeLiveData() {
        viewModel.pullLiveData.observe(this) {
            binding.swipeRefresh.isRefreshing = false
            loading = true
            it?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.progress.makeGone()
                        it.data?.let {
                            adapter.updateList(it)
                        }
                        checkEmpty()
                    }
                    Status.LOADING -> {
                        binding.progress.makeVisible()
                    }
                    Status.ERROR -> {
                        binding.progress.makeGone()
                        it.message?.let {
                            showToast(it)
                        }
                    }
                }
            }
        }
    }

    override fun setListener() {
        super.setListener()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshPage()
            adapter.refresh()
            fetchData()
        }

        binding.rvPullRequest.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) { //check for scroll down
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false

                            viewModel.updatePage()
                            fetchData()
                        }
                    }
                }
            }
        })
    }

    private fun checkEmpty() {
        if (adapter.itemCount == 0) {
            showToast(getString(R.string.error_no_result))
        }
    }

    override fun getViewBinding(): ActivityPullRequestBinding {
        return ActivityPullRequestBinding.inflate(layoutInflater)
    }
}