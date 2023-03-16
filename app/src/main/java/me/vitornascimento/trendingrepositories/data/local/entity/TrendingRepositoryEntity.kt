package me.vitornascimento.trendingrepositories.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trending_repositories")
data class TrendingRepositoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    @ColumnInfo(name = "stars_count") val starsCount: Int,
    @ColumnInfo(name = "forks_count") val forksCount: Int,
    @ColumnInfo(name = "owner_username") val ownerUsername: String,
    @ColumnInfo(name = "owner_avatar_url") val ownerAvatarUrl: String,
    val page: Int,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
)