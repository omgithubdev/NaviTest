package com.omagrahari.navitest.ui.pulls.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omagrahari.navitest.R
import com.omagrahari.navitest.databinding.RowPullRequestBinding
import com.omagrahari.navitest.model.PullResponse
import com.omagrahari.navitest.util.DateUtils.getFormattedDate
import javax.inject.Inject

class PullRequestAdapter @Inject constructor() :
    RecyclerView.Adapter<PullRequestAdapter.PullRequestViewHolder>() {

    private val pullRequestList = ArrayList<PullResponse>()

    fun updateList(pullRequestList: List<PullResponse>) {
        this.pullRequestList.addAll(pullRequestList)
        notifyDataSetChanged()
    }

    fun refresh() {
        this.pullRequestList.clear()
        notifyDataSetChanged()
    }

    inner class PullRequestViewHolder(private val binding: RowPullRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(pullResponse: PullResponse) {
            val context = binding.root.context

            binding.tvTitle.text = pullResponse.title

            getFormattedDate(pullResponse.closed_at)?.let {
                binding.tvClosedAt.text = context.getString(R.string.label_closed_at, it)
            }
            getFormattedDate(pullResponse.created_at)?.let {
                binding.tvCreatedAt.text = context.getString(R.string.label_created_at, it)
            }

            binding.tvUserName.text = pullResponse.user.login

            Glide.with(context).load(pullResponse.user.avatar_url).into(binding.ivProfile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        return PullRequestViewHolder(
            RowPullRequestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        holder.bindData(pullRequestList[position])
    }

    override fun getItemCount(): Int {
        return pullRequestList.size
    }
}