package ru.valya1.skyengtest.common.presentation

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface BaseMvpView : MvpView {
    
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setLoadingState(isLoading: Boolean)
    
    @StateStrategyType(SkipStrategy::class)
    fun showError(errorText: String)
}