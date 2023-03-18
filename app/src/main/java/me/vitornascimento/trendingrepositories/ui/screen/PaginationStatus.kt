package me.vitornascimento.trendingrepositories.ui.screen

enum class PaginationStatus {
    IDLE,
    LOADING,
    ERROR,
    PAGINATING,
    PAGINATING_ERROR,
    PAGINATION_EXHAUST,
}