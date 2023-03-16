package me.vitornascimento.trendingrepositories.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RepositoryOwnerInfoDTO(
    @SerializedName("login")
    val username: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
)
