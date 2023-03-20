package me.vitornascimento.trendingrepositories.data.remote

import me.vitornascimento.trendingrepositories.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

object GithubServiceAuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithAuthorization = chain.request().newBuilder()
            .addHeader("Authorization", BuildConfig.GITHUB_TOKEN)
            .build()

        return chain.proceed(requestWithAuthorization)
    }
}