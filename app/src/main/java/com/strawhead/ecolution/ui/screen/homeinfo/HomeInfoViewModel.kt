package com.strawhead.ecolution.ui.screen.homeinfo

import android.util.Log
import androidx.lifecycle.ViewModel
import com.strawhead.ecolution.data.remote.response.GetAllHouseResponseItem
import com.strawhead.ecolution.data.remote.response.GetFloodPredictionResponse
import com.strawhead.ecolution.data.remote.retrofit.ApiConfig
import com.strawhead.ecolution.data.remote.retrofit.ApiConfigML
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeInfoViewModel() : ViewModel() {
    private val _state = MutableStateFlow<GetFloodPredictionResponse?>(null)
    val state = _state.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()
    fun reload(kota: String) {
        _loading.value = true
        val client = ApiConfigML.getApiService().getPrediction(kota)
        client.enqueue(object : Callback<GetFloodPredictionResponse> {
            override fun onResponse(
                call: Call<GetFloodPredictionResponse>,
                response: Response<GetFloodPredictionResponse>
            ) {
                _loading.value = false
                val responseBody = response.body()
                Log.d("response body", responseBody.toString())
                _state.value = responseBody!!

            }
            override fun onFailure(call: Call<GetFloodPredictionResponse>, t: Throwable) {
                Log.e("error", "onFailure: ${t.message}")
                _loading.value = false
            }
        })
    }
}