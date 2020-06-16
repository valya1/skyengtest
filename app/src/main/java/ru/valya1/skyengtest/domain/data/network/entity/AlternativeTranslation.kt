package ru.valya1.skyengtest.domain.data.network.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlternativeTranslation(
    @SerialName("text") val text: String? = null,
    @SerialName("translation") val translation: String? = null
)