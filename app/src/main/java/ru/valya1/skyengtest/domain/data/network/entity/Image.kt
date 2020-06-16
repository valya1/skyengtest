package ru.valya1.skyengtest.domain.data.network.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    @SerialName("url") val url: String? = null
)