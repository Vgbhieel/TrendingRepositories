package me.vitornascimento.trendingrepositories.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.vitornascimento.trendingrepositories.data.local.dao.TrendingRepositoriesDao
import me.vitornascimento.trendingrepositories.data.local.entity.TrendingRepositoryEntity

@Database(
    entities = [TrendingRepositoryEntity::class],
    version = 1,
)
abstract class TrendingRepositoriesDataBase: RoomDatabase() {
    abstract fun getTrendingRepositoriesDao(): TrendingRepositoriesDao
}