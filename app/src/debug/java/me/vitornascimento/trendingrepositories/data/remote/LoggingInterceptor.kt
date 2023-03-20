package me.vitornascimento.trendingrepositories.data.remote

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

object LoggingInterceptor {
    operator fun invoke() = HttpLoggingInterceptor {
        Log.d("Retrofit Client", it)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}
