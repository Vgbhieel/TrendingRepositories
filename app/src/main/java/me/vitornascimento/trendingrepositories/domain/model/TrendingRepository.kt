package me.vitornascimento.trendingrepositories.domain.model

data class TrendingRepository(
    val name: String,
    val starsCount: Int,
    val forksCount: Int,
    val ownerUsername: String,
    val ownerAvatarUrl: String,
)
