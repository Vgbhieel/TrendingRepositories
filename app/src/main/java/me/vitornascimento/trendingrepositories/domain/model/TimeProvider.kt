package me.vitornascimento.trendingrepositories.domain.model

object TimeProvider {

    fun nowInMillis(): Long = System.currentTimeMillis()
}