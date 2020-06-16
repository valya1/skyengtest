package ru.valya1.skyengtest.domain.usecase.base

sealed class Result<T> {
    
    data class Success<T>(val result: T) : Result<T>()
    data class Failure<T>(val exception: Throwable) : Result<T>()
}