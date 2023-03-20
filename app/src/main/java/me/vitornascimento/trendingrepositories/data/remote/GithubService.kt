package me.vitornascimento.trendingrepositories.data.remote

import me.vitornascimento.trendingrepositories.data.remote.dto.TrendingRepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories?q=language:kotlin&sort=stars&per_page=20")
    suspend fun getTrendingRepositories(
        @Query("page") page: Int,
    ): TrendingRepositoriesResponse
}