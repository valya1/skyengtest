package ru.valya1.skyengtest.domain.data.network

import io.reactivex.Single
import retrofit2.Response
import ru.valya1.skyengtest.common.exception.DataLoadingException

fun <T> Single<Response<T>>.parseOrError(): Single<T> {
    return map {
        if (!it.isSuccessful) throw DataLoadingException("Ошибка загрузки данных")
        it.body() ?: throw DataLoadingException("Ошибка загрузки данных")
    }
}