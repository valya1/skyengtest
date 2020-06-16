package ru.valya1.skyengtest.domain.usecase.meanings

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.internal.operators.single.SingleJust
import io.reactivex.schedulers.Schedulers
import ru.valya1.skyengtest.domain.data.network.entity.WordMeaningItem
import ru.valya1.skyengtest.domain.usecase.base.PeriodicDataLoadingUseCase
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class LoadMeanignsUseCase @Inject constructor(
    private val searchMeaningsUseCase: SearchMeaningsUseCase
) : PeriodicDataLoadingUseCase<LoadMeanignsUseCase.Response, LoadMeanignsUseCase.Param>() {
    
    override val loadingDataStrategy: LoadingDataStrategy = LoadingDataStrategy.CANCEL_PREVIOUS
    
    override fun Observable<Param>.mapRequestChain(): Observable<Param> {
        return distinctUntilChanged()
            .debounce(300, TimeUnit.MILLISECONDS, Schedulers.io())
    }
    
    override fun getData(param: Param): Single<Response> {
        
        return if (param.query.isEmpty()) {
            SingleJust(Response("", emptyList()))
        } else {
            searchMeaningsUseCase(SearchMeaningsUseCase.Param(param.query, 20, 1))
                .map { Response(param.query, it) }
                .onErrorReturn { Response(param.query, emptyList()) }
        }
    }
    
    data class Param(val query: String)
    
    data class Response(val requestedQuery: String, val meanings: List<WordMeaningItem>)
}