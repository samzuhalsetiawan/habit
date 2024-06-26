package com.samzuhalsetiawan.habbits.repository

sealed class MainRepositoryResult<T> {
    data class Success<T>(val data: T): MainRepositoryResult<T>()
    data class Failed<T>(val errorMessage: String): MainRepositoryResult<T>()
}