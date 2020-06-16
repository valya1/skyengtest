package ru.valya1.skyengtest.presentation.meanings_list.list

import ru.valya1.skyengtest.domain.data.network.entity.WordMeaningItem

fun WordMeaningItem.asListMeaningItem(): MeaningListItem {
    return if (meanings.size == 1)
        MeaningListItem.SingleMeaningListItem(this)
    else
        MeaningListItem.MultipleMeaningListItem(this)
}