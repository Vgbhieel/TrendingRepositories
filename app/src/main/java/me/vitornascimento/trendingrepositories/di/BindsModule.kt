package me.vitornascimento.trendingrepositories.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.vitornascimento.trendingrepositories.data.repository.TrendingRepositoriesRepositoryImpl
import me.vitornascimento.trendingrepositories.domain.repository.TrendingRepositoriesRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

    @Binds
    abstract fun bindTrendingRepositoriesRepository(
        implementation: TrendingRepositoriesRepositoryImpl
    ): TrendingRepositoriesRepository
}