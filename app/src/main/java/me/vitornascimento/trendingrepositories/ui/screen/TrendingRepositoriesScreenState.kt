package me.vitornascimento.trendingrepositories.ui.screen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import me.vitornascimento.trendingrepositories.domain.model.TrendingRepository

@Parcelize
data class TrendingRepositoriesScreenState(
    val trendingRepositories: List<TrendingRepository>,
    val paginationStatus: PaginationStatus,
) : Parcelable
