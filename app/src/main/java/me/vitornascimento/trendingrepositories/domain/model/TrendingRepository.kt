package me.vitornascimento.trendingrepositories.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrendingRepository(
    val name: String,
    val starsCount: Int,
    val forksCount: Int,
    val ownerUsername: String,
    val ownerAvatarUrl: String,
) : Parcelable
