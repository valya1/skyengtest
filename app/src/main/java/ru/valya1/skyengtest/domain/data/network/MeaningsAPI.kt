package ru.valya1.skyengtest.domain.data.network

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.valya1.skyengtest.domain.data.network.entity.MeaningDetailsItem
import ru.valya1.skyengtest.domain.data.network.entity.WordMeaningItem

interface MeaningsAPI {
    
    @GET("v1/words/search")
    fun searchMeanings(
        @Query("search") query: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): Single<Response<List<WordMeaningItem>>>
    
    @GET("v1/meanings")
    fun getMeanings(
        @Query("ids") ids: List<Int>
    ): Single<Response<List<MeaningDetailsItem>>>
    
}