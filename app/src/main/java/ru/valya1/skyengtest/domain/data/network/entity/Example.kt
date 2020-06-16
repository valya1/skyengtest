package ru.valya1.skyengtest.domain.data.network.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Example(
    @SerialName("soundUrl") val soundUrl: String? = null,
    @SerialName("text") val text: String? = null
)