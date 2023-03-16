package me.vitornascimento.trendingrepositories.data.remote

import okhttp3.logging.HttpLoggingInterceptor

object LoggingInterceptor {
    operator fun invoke() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.NONE
    }
}
