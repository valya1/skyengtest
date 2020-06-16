package ru.valya1.skyengtest.presentation.meaning_details

import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.valya1.skyengtest.common.presentation.BaseMvpView
import ru.valya1.skyengtest.common.presentation.BasePresenter
import ru.valya1.skyengtest.domain.data.network.entity.MeaningDetailsItem
import ru.valya1.skyengtest.domain.usecase.meanings.GetMeaningDetailsUseCase

interface MeaningDetailsView : BaseMvpView {
    
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showDetails(meaningDetails: MeaningDetailsItem)
    
}

class MeaningDetailsPresenter @AssistedInject constructor(
    @Assisted val meaningId: Int,
    private val getMeaningDetailsUseCase: GetMeaningDetailsUseCase
) : BasePresenter<MeaningDetailsView>() {
    
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        
        getMeaningDetailsUseCase.executeWith(
            params = meaningId,
            withSpinner = true,
            onResult = { meaningDetailsItems -> viewState.showDetails(meaningDetailsItems[0]) }
        )
        
    }
    
    @AssistedInject.Factory
    interface Factory {
        fun createPresenter(meaningId: Int): MeaningDetailsPresenter
    }
}
