package me.vitornascimento.trendingrepositories.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TrendingRepositoriesResponse(
    @SerializedName(value = "items")
    val trendingRepositories: List<TrendingRepositoryDTO>
)