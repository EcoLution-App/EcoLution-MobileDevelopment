package com.strawhead.ecolution.ui.screen.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.strawhead.ecolution.data.remote.response.GetAllHouseResponseItem
import com.strawhead.ecolution.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceOwnedViewModel: ViewModel() {
    private val _stateData = MutableStateFlow<List<GetAllHouseResponseItem>>(emptyList())
    val stateData = _stateData.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()
    fun reload() {
        _loading.value = true
        val client = ApiConfig.getApiService().getAllHouse()
        client.enqueue(object : Callback<List<GetAllHouseResponseItem>> {
            override fun onResponse(
                call: Call<List<GetAllHouseResponseItem>>,
                response: Response<List<GetAllHouseResponseItem>>
            ) {
                _loading.value = false
                val responseBody = response.body()
                Log.d("response body", responseBody.toString())
                _stateData.value = responseBody!!
            }
            override fun onFailure(call: Call<List<GetAllHouseResponseItem>>, t: Throwable) {
                Log.e("error", "onFailure: ${t.message}")
                _loading.value = false
            }
        })
    }
}