package ru.valya1.skyengtest.domain.usecase.base

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

interface UseCase<C, P, RType> {
    val subscribeScheduler: Scheduler get() = Schedulers.io()
    
    fun execute(param: P): RType
}