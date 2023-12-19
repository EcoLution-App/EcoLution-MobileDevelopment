package com.strawhead.ecolution.data.remote.retrofit

import com.strawhead.ecolution.data.remote.response.DeleteHomeResponse
import com.strawhead.ecolution.data.remote.response.GetAllHouseResponse
import com.strawhead.ecolution.data.remote.response.GetAllHouseResponseItem
import com.strawhead.ecolution.data.remote.response.PostHomeResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @Multipart
    @POST("houses")
    suspend fun uploadHouseData(
        @Part("title") title: RequestBody,
        @Part("price") price: RequestBody,
        @Part("description") description: RequestBody,
        @Part("seller") seller: RequestBody,
        @Part("email") email: RequestBody,
        @Part("address") address: RequestBody,
        @Part("subdistrict") subdistrict: RequestBody,
        @Part file: MultipartBody.Part,
    ): PostHomeResponse

    @GET("houses")
    fun getAllHouse(): Call<List<GetAllHouseResponseItem>>

    @DELETE("houses/{id}")
    suspend fun deleteHouse(@Path("id") id: String): DeleteHomeResponse
}