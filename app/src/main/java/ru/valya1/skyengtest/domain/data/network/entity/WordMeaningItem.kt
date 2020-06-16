package ru.valya1.skyengtest.domain.data.network.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WordMeaningItem(
    @SerialName("id") val id: Int?,
    @SerialName("meanings") val meanings: List<Meaning>,
    @SerialName("text") val text: String?
)