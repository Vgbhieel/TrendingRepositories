package me.vitornascimento.trendingrepositories.data.mapper

import me.vitornascimento.trendingrepositories.data.local.entity.TrendingRepositoryEntity
import me.vitornascimento.trendingrepositories.data.remote.dto.TrendingRepositoriesResponse
import me.vitornascimento.trendingrepositories.domain.model.TrendingRepository

fun TrendingRepositoryEntity.toDomainModel(): TrendingRepository = TrendingRepository(
    name = this.name,
    starsCount = this.starsCount,
    forksCount = this.forksCount,
    ownerUsername = this.ownerUsername,
    ownerAvatarUrl = this.ownerAvatarUrl,
)

fun TrendingRepositoriesResponse.toEntityModelList(page: Int): List<TrendingRepositoryEntity> =
    this.trendingRepositories.map {
        TrendingRepositoryEntity(
            id = it.id,
            name = it.name,
            starsCount = it.starsCount,
            forksCount = it.forksCount,
            ownerUsername = it.ownerInfo.username,
            ownerAvatarUrl = it.ownerInfo.avatarUrl,
            page = page,
        )
    }