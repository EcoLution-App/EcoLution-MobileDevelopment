package com.strawhead.ecolution.data.remote.retrofit

import com.strawhead.ecolution.data.remote.response.DeleteHomeResponse
import com.strawhead.ecolution.data.remote.response.GetAllHouseResponse
import com.strawhead.ecolution.data.remote.response.GetAllHouseResponseItem
import com.strawhead.ecolution.data.remote.response.GetFloodPredictionResponse
import com.strawhead.ecolution.data.remote.response.PostHomeResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiServiceML {
    @FormUrlEncoded
    @POST("/")
    fun getPrediction(
        @Field("nama_kabupaten_kota") name: String
    ): Call<GetFloodPredictionResponse>

}