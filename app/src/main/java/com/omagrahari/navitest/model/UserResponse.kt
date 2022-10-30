package com.omagrahari.navitest.model

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @SerializedName("id")
    val id: String,

    @SerializedName("avatar_url")
    val avatar_url: String,

    @SerializedName("login")
    val login: String

)
