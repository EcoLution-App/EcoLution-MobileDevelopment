package com.strawhead.ecolution.ui.screen.home

import android.annotation.SuppressLint
import android.icu.text.NumberFormat
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.strawhead.ecolution.data.remote.response.GetAllHouseResponse
import com.strawhead.ecolution.data.remote.response.GetAllHouseResponseItem
import com.strawhead.ecolution.data.remote.retrofit.ApiConfig
import com.strawhead.ecolution.ui.components.Search
import com.strawhead.ecolution.ui.screen.addhome.LocationDataStore
import com.strawhead.ecolution.ui.tempmodel.PlaceInfo
import com.strawhead.ecolution.ui.tempmodel.dummyPlaceInfo
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(navigateToPlace: (image: String,
                                 title: String,
                                 price: String,
                                 address: String,
                                 description: String,
                                 sellerName: String,
                                 sellerEmail: String) -> Unit) {
    val homeViewModel = viewModel(modelClass = HomeScreenViewModel::class.java)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
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
                    Text(
                        text = "Latest sales",
                        modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp),
                        fontWeight = FontWeight.Bold,
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
                    ) {
                        items(state!!) { place ->
                            NearYouItem(place, Modifier.shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    navigateToPlace(place!!.imageUrl!!, place!!.title!!, place!!.price!!, place!!.address!!, place!!.description!!, place!!.seller!!, place!!.email!!)
                                }
                            )
                        }
                    }

                    Text(
                        text = "Based on your preference",
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
                        fontWeight = FontWeight.Bold,
                    )

                    LazyColumn(modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 20.dp)) {
                        items(state!!) { place ->
                            RecommendedItem(place, modifier = Modifier
                                .clickable {
                                    navigateToPlace(place!!.imageUrl!!, place!!.title!!, place!!.price!!, place!!.address!!, place!!.description!!, place!!.seller!!, place!!.email!!)
                                })
                        }
                    }

            }

        } else {
            Box {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = Color(0xffeae5e7),
                    )
                }
            }

        }
    }
}

@Composable
fun Banner(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Surface(
            color = Color(0xFF425A75), modifier = Modifier
                .fillMaxWidth()
        ) {
            Search()
        }
    }
}

@Composable
fun RecommendedItem(
    place: GetAllHouseResponseItem?,
    modifier: Modifier = Modifier
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
                Text(
                    text = place!!.title!!,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp, start = 16.dp)
                )
                Text(
                    text = place!!.address!!.take(20)+"...",
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
        }
    }
}

@Composable
fun NearYouItem(
    place: GetAllHouseResponseItem?,
    modifier: Modifier = Modifier,
) {
    val myIndonesianLocale = Locale("in", "ID")
    val format: NumberFormat = NumberFormat.getCurrencyInstance(myIndonesianLocale)
    Card (
        modifier = modifier.width(140.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    ){
        Column {
            AsyncImage(
                model = place!!.imageUrl!!,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(modifier = Modifier.padding(8.dp)) {
                if (place!!.title!!.length > 15) {
                    Text(
                        text = place!!.title!!.take(13)+"..",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold
                        ),
                    )
                } else {
                    Text(
                        text = place!!.title!!,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold
                        ),
                    )
                }
                Text(
                    text = place!!.address!!.substring(0, 13) + "...",
                    maxLines = 1,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Light,
                )
                Text(
                    text = format.format(place!!.price!!.toInt()).dropLast(3),
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}

//@Preview(showBackground = true, device = Devices.PIXEL_4)
//@Composable
//fun PreviewHome() {
//    HomeScreen({})
//}

//@Composable
//@Preview(showBackground = true)
//fun NearYouItemPreview() {
//    NearYouItem(
//        place = PlaceInfo(R.drawable.photo_rumah, "FNAF Pizzeria", "Backroom street number 420", 6.9),
//        modifier = Modifier.padding(8.dp)
//    )
//}
//
//@Composable
//@Preview(showBackground = true)
//fun RecommendedItemPreview() {
//    RecommendedItem(
//        place = PlaceInfo(R.drawable.photo_rumah, "FNAF Pizzeria", "Backroom street number 420", 6.9),
//        modifier = Modifier.padding(8.dp)
//    )
//}


//else {
//    Box {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            CircularProgressIndicator(
//                modifier = Modifier.width(64.dp),
//                color = MaterialTheme.colorScheme.secondary,
//                trackColor = Color(0xffeae5e7),
//            )
//        }
//    }
//
//}