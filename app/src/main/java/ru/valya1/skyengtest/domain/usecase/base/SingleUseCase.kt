package ru.valya1.skyengtest.domain.usecase.base

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject

abstract class SingleUseCase<C, P> : UseCase<Result<C>, P, Single<Result<C>>> {
    
    abstract operator fun invoke(param: P): Single<C>
    
    override fun execute(param: P): Single<Result<C>> {
        return invoke(param)
            .map<Result<C>> { Result.Success(it) }
            .onErrorReturn { Result.Failure(it) }
    }
    
}

abstract class ObservableUseCase<C, P> : UseCase<Result<C>, P, Observable<Result<C>>> {
    
    abstract operator fun invoke(param: P): Observable<C>
    
    override fun execute(param: P): Observable<Result<C>> {
        return invoke(param)
            .map<Result<C>> { Result.Success(it) }
            .onErrorReturn { Result.Failure(it) }
    }
    
}

abstract class PeriodicDataLoadingUseCase<TCriteria, TParam> :
    ObservableUseCase<TCriteria, Unit>() {
    
    @Volatile
    var onStartLoading: ((TParam) -> Unit)? = null
    
    protected open val loadingDataStrategy = LoadingDataStrategy.KEEP_ORDER
    
    private val pager = BehaviorSubject.create<TParam>()
    
    protected abstract fun getData(param: TParam): Single<TCriteria>
    
    protected open fun Observable<TParam>.mapRequestChain(): Observable<TParam> = this
    
    fun loadNext(params: TParam) = pager.onNext(params)
    
    override fun invoke(param: Unit): Observable<TCriteria> = pager
        .compose { it.mapRequestChain() }
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext { onStartLoading?.invoke(it) }
        .compose { observable ->
            when (loadingDataStrategy) {
                LoadingDataStrategy.ANY_ORDER -> observable.flatMapSingle {
                    getData(it).subscribeOn(subscribeScheduler)
                }
                LoadingDataStrategy.KEEP_ORDER -> observable.concatMapSingle {
                    getData(it).subscribeOn(subscribeScheduler)
                }
                LoadingDataStrategy.CANCEL_PREVIOUS -> observable.switchMapSingle {
                    getData(it).subscribeOn(subscribeScheduler)
                }
            }
        }
    
    enum class LoadingDataStrategy {
        KEEP_ORDER, ANY_ORDER, CANCEL_PREVIOUS
    }
}