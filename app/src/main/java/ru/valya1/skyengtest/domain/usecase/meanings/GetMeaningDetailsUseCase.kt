package ru.valya1.skyengtest.domain.usecase.meanings

import io.reactivex.Single
import ru.valya1.skyengtest.domain.data.network.MeaningsAPI
import ru.valya1.skyengtest.domain.data.network.entity.MeaningDetailsItem
import ru.valya1.skyengtest.domain.data.network.parseOrError
import ru.valya1.skyengtest.domain.usecase.base.Result
import ru.valya1.skyengtest.domain.usecase.base.SingleUseCase
import javax.inject.Inject

class GetMeaningDetailsUseCase @Inject constructor(
    private val api: MeaningsAPI
) : SingleUseCase<List<MeaningDetailsItem>, Int>() {
    
    override fun invoke(param: Int): Single<List<MeaningDetailsItem>> {
        return api.getMeanings(listOf(param)).parseOrError()
    }
    
}