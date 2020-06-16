package ru.valya1.skyengtest.domain.data.network.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meaning(
    @SerialName("id") val id: Int?,
    @SerialName("imageUrl") val imageUrl: String? = null,
    @SerialName("partOfSpeechCode") val partOfSpeechCode: String? = null,
    @SerialName("previewUrl") val previewUrl: String? = null,
    @SerialName("soundUrl") val soundUrl: String? = null,
    @SerialName("transcription") val transcription: String? = null,
    @SerialName("translation") val translation: Translation? = null
)