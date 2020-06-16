package ru.valya1.skyengtest.domain.data.network.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeaningDetailsItem(
    @SerialName("definition") val definition: Definition? = null,
    @SerialName("examples") val examples: List<Example>? = null,
    @SerialName("id") val id: String? = null,
    @SerialName("images") val images: List<Image>? = null,
    @SerialName("partOfSpeechCode") val partOfSpeechCode: String? = null,
    @SerialName("soundUrl") val soundUrl: String? = null,
    @SerialName("text") val text: String? = null,
    @SerialName("translation") val translation: Translation? = null,
    @SerialName("transcription") val transcription: String? = null,
    @SerialName("updatedAt") val updatedAt: String? = null,
    @SerialName("wordId") val wordId: Int? = null
)