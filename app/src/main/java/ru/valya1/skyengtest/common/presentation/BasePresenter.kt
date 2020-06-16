package ru.valya1.skyengtest.common.presentation

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiConsumer
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.addTo
import moxy.MvpPresenter
import ru.valya1.skyengtest.common.exception.DataLoadingException
import ru.valya1.skyengtest.common.exception.UIException
import ru.valya1.skyengtest.domain.usecase.base.ObservableUseCase
import ru.valya1.skyengtest.domain.usecase.base.PeriodicDataLoadingUseCase
import ru.valya1.skyengtest.domain.usecase.base.Result
import ru.valya1.skyengtest.domain.usecase.base.SingleUseCase
import ru.valya1.skyengtest.domain.usecase.base.UseCase

abstract class BasePresenter<V : BaseMvpView> : MvpPresenter<V>() {
    
    protected val compositeDisposable = CompositeDisposable()
    
    protected val defaultErrorConsumer = Consumer<Throwable> { throwable ->
        when (throwable) {
            is UIException -> viewState.showError(throwable.description)
            else -> throwable.printStackTrace()
        }
    }
    
    fun <C, P> SingleUseCase<C, P>.executeWith(
        params: P,
        observeScheduler: Scheduler = AndroidSchedulers.mainThread(),
        withSpinner: Boolean = false,
        showLoading: () -> Unit = { (viewState as? BaseMvpView)?.setLoadingState(true) },
        hideLoading: () -> Unit = { (viewState as? BaseMvpView)?.setLoadingState(false) },
        onResult: (C) -> Unit = {},
        onError: (Throwable) -> Unit = defaultErrorConsumer::accept
    ): Disposable {
        return execute(params)
            .subscribeOn(subscribeScheduler)
            .observeOn(observeScheduler)
            .doOnSubscribe { if (withSpinner) showLoading() }
            .doOnEvent { _, _ -> if (withSpinner) hideLoading() }
            .subscribe(
                { result ->
                    when (result) {
                        is Result.Success -> onResult(result.result)
                        is Result.Failure -> onError(result.exception)
                    }
                },
                defaultErrorConsumer::accept
            )
            .addTo(compositeDisposable)
    }
    
    fun <C, P> PeriodicDataLoadingUseCase<C, P>.executeWith(
        onStartLoading: ((P) -> Unit)? = null,
        onFinishLoading: (() -> Unit)? = null,
        observeScheduler: Scheduler = AndroidSchedulers.mainThread(),
        onResult: (C) -> Unit = {},
        onError: (Throwable) -> Unit = defaultErrorConsumer::accept
    ): Disposable {
        
        return execute(Unit)
            .subscribeOn(subscribeScheduler)
            .observeOn(observeScheduler)
            .doOnEach { onFinishLoading?.invoke() }
            .doOnError { onFinishLoading?.invoke() }
            .subscribe(
                { result ->
                    when (result) {
                        is Result.Success -> onResult(result.result)
                        is Result.Failure -> onError(result.exception)
                    }
                },
                defaultErrorConsumer::accept
            )
            .addTo(compositeDisposable)
            .also { this.onStartLoading = onStartLoading }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}