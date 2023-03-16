package me.vitornascimento.trendingrepositories.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import me.vitornascimento.trendingrepositories.data.local.TrendingRepositoriesDataBase
import me.vitornascimento.trendingrepositories.data.local.dao.TrendingRepositoriesDao
import me.vitornascimento.trendingrepositories.data.remote.GithubService
import me.vitornascimento.trendingrepositories.data.remote.GithubServiceAuthorizationInterceptor
import me.vitornascimento.trendingrepositories.data.remote.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ProvidersModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor())
        .addInterceptor(GithubServiceAuthorizationInterceptor)
        .build()

    @Provides
    fun provideGithubService(
        okHttpClient: OkHttpClient,
    ): GithubService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl("https://api.github.com/")
            .build()
            .create(GithubService::class.java)
    }

    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideTrendingRepositoriesDataBase(@ApplicationContext context: Context): TrendingRepositoriesDataBase =
        Room
            .databaseBuilder(
                context,
                TrendingRepositoriesDataBase::class.java,
                "trending_repositories_database"
            )
            .build()

    @Provides
    fun provideTrendingRepositoriesDao(dataBase: TrendingRepositoriesDataBase): TrendingRepositoriesDao =
        dataBase.getTrendingRepositoriesDao()

}