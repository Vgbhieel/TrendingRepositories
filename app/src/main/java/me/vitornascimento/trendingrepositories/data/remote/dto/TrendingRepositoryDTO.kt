package me.vitornascimento.trendingrepositories.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TrendingRepositoryDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("stargazers_count")
    val starsCount: Int,
    @SerializedName("forks")
    val forksCount: Int,
    @SerializedName("owner")
    val ownerInfo: RepositoryOwnerInfoDTO,
)
