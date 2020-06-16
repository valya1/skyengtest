package ru.valya1.skyengtest.presentation.meanings_list

import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.valya1.skyengtest.common.presentation.BaseMvpView
import ru.valya1.skyengtest.common.presentation.BasePresenter
import ru.valya1.skyengtest.domain.data.network.entity.Meaning
import ru.valya1.skyengtest.domain.usecase.meanings.LoadMeanignsUseCase
import ru.valya1.skyengtest.presentation.meanings_list.list.MeaningListItem
import ru.valya1.skyengtest.presentation.meanings_list.list.asListMeaningItem
import javax.inject.Inject

interface MeaningsListView : BaseMvpView {
    
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openMeaning(meaning: Meaning)
    
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun submitMeaningsSearchResult(result: MeaningsSearchResult)
    
    sealed class MeaningsSearchResult {
        data class MeaningsResult(val meanings: List<MeaningListItem>) : MeaningsSearchResult()
        object EmptyResult : MeaningsSearchResult()
        object EmptySearch : MeaningsSearchResult()
    }
    
}

class MeaningsListPresenter @Inject constructor(
    private val loadMeaningsUseCase: LoadMeanignsUseCase
) : BasePresenter<MeaningsListView>() {
    
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadMeaningsUseCase.executeWith(
            onStartLoading = { viewState.setLoadingState(true) },
            onFinishLoading = { viewState.setLoadingState(false) },
            onResult = { (query, meanings) ->
                
                when {
                    meanings.isNotEmpty() -> {
                        viewState.submitMeaningsSearchResult(
                            MeaningsListView.MeaningsSearchResult.MeaningsResult(
                                meanings.map { it.asListMeaningItem() }
                            )
                        )
                    }
                    query.isEmpty() -> {
                        viewState.submitMeaningsSearchResult(
                            MeaningsListView.MeaningsSearchResult.EmptySearch
                        )
                    }
                    
                    else -> {
                        viewState.submitMeaningsSearchResult(
                            MeaningsListView.MeaningsSearchResult.EmptyResult
                        )
                    }
                }
            }
        )
    }
    
    fun submitQuery(query: String) = loadMeaningsUseCase.loadNext(LoadMeanignsUseCase.Param(query))
    
    fun onMeaningClicked(meaning: Meaning) = viewState.openMeaning(meaning)
    
}