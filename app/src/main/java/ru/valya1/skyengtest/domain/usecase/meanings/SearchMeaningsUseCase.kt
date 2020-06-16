package ru.valya1.skyengtest.domain.usecase.meanings

import io.reactivex.Single
import ru.valya1.skyengtest.domain.data.network.MeaningsAPI
import ru.valya1.skyengtest.domain.data.network.entity.WordMeaningItem
import ru.valya1.skyengtest.domain.data.network.parseOrError
import ru.valya1.skyengtest.domain.usecase.base.SingleUseCase
import javax.inject.Inject

class SearchMeaningsUseCase @Inject constructor(private val api: MeaningsAPI) :
    SingleUseCase<List<WordMeaningItem>, SearchMeaningsUseCase.Param>() {
    
    override fun invoke(param: Param): Single<List<WordMeaningItem>> {
        val (query, limit, page) = param
        return api.searchMeanings(query, limit, page).parseOrError()
    }
    
    data class Param(
        val query: String,
        val limit: Int,
        val page: Int
    )
}
