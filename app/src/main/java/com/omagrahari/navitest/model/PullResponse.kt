package com.omagrahari.navitest.model

import com.google.gson.annotations.SerializedName

data class PullResponse(

    @SerializedName("id")
    val id: Int,

    @SerializedName("url")
    val url: String,

    @SerializedName("state")
    val state: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("created_at")
    val created_at: String,

    @SerializedName("closed_at")
    val closed_at: String,

    @SerializedName("user")
    val user: UserResponse

)
