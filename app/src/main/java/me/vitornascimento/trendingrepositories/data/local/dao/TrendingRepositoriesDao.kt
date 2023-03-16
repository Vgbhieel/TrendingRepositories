package me.vitornascimento.trendingrepositories.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.vitornascimento.trendingrepositories.data.local.entity.TrendingRepositoryEntity

@Dao
interface TrendingRepositoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<TrendingRepositoryEntity>)

    @Query("SELECT * FROM trending_repositories ORDER BY stars_count DESC")
    suspend fun getTrendingRepositories(): List<TrendingRepositoryEntity>

    @Query("DELETE FROM trending_repositories")
    suspend fun clearAllTrendingRepositories()
}