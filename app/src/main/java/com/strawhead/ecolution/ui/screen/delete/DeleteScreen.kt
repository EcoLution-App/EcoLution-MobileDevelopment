package com.strawhead.ecolution.ui.screen.delete

import android.annotation.SuppressLint
import android.icu.text.NumberFormat
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.strawhead.ecolution.data.remote.response.GetAllHouseResponseItem
import com.strawhead.ecolution.data.remote.response.PostHomeResponse
import com.strawhead.ecolution.data.remote.retrofit.ApiConfig
import com.strawhead.ecolution.signin.UserData
import com.strawhead.ecolution.ui.components.Search
import com.strawhead.ecolution.ui.screen.home.HomeScreenViewModel
import com.strawhead.ecolution.ui.screen.home.ReloadDataStore
import com.strawhead.ecolution.ui.screen.profile.ReloadDataStoreForAcc
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.Locale

@Composable
fun Banner(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Surface(
            color = Color(0xFF425A75), modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("My Sales", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 20.dp))
        }
    }
}
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DeleteScreen(userData: UserData?, showToast: (String) -> Unit,
                 navigateToPlace: (image: String,
                                   title: String,
                                   price: String,
                                   address: String,
                                   description: String,
                                   sellerName: String,
                                   sellerEmail: String) -> Unit) {
    val homeViewModel = viewModel(modelClass = HomeScreenViewModel::class.java)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val reloadDataStoreAcc = ReloadDataStoreForAcc(context)
    val dataStore = ReloadDataStore(context)
    val reload = dataStore.getReload.collectAsState(initial = "true")
    val state by homeViewModel.state.collectAsState()
    val loading by homeViewModel.loading.collectAsState()
    if (reload.value == "true") {
        homeViewModel.reload()
        scope.launch {
            dataStore.saveReload("false")
        }
    }

    Column() {
        Banner()
        if(!loading) {
            if (!state.isEmpty()) {
                LazyColumn(modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, bottom = 20.dp, top = 20.dp)) {
                    items(state!!) { place ->
                        if(place.email == userData!!.email) {
                            RecommendedItem(place, modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navigateToPlace(place!!.imageUrl!!, place!!.title!!, place!!.price!!, place!!.address!!, place!!.description!!, place!!.seller!!, place!!.email!!)
                                }
                            , delete = {id ->
                                    scope.launch {
                                        try {
                                            val apiService = ApiConfig.getApiService()
                                            val successResponse = apiService.deleteHouse(id)
                                            Log.d("status delete data", successResponse.message!!)
                                            if(successResponse.message!! == "House deleted successfully") {
                                                showToast(successResponse.message!!)
                                                homeViewModel.reload()
                                                reloadDataStoreAcc.saveReload("true")
                                            }
                                        } catch (e: HttpException) {
                                            val errorBody = e.response()?.errorBody()?.string()
                                            val errorResponse = Gson().fromJson(errorBody, PostHomeResponse::class.java)
                                            Log.d("status delete data", errorResponse.message!!)
                                            showToast(errorResponse.message!!)
                                        }
                                    }
                                })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecommendedItem(
    place: GetAllHouseResponseItem?,
    modifier: Modifier = Modifier,
    delete: (String) -> Unit
) {
    val myIndonesianLocale = Locale("in", "ID")
    val format: NumberFormat = NumberFormat.getCurrencyInstance(myIndonesianLocale)
    Card (
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.padding(vertical = 8.dp, horizontal = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xff9ba8b6),
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = place!!.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(115.dp)
            )
            Column () {
                if (place!!.title!!.length > 15) {
                    Text(
                        text = place!!.title!!.take(15) + "...",
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp, start = 16.dp)
                    )
                } else {
                    Text(
                        text = place!!.title!!,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp, start = 16.dp)
                    )
                }
                Text(
                    text = place!!.address!!.take(15)+"...",
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 10.dp, end = 10.dp)
                )
                Text(
                    text = format.format(place!!.price!!.toInt()).dropLast(3),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 10.dp, end = 10.dp)
                )
            }
            Icon(
                Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp).clickable {
                    delete(place!!.id)
                }
            )
        }
    }
}