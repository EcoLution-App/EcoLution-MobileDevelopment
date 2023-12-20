package com.strawhead.ecolution.ui.screen.bookmark

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.NumberFormat
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.yml.charts.common.extensions.isNotNull
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.strawhead.ecolution.R
import com.strawhead.ecolution.data.local.database.Bookmark
import com.strawhead.ecolution.data.remote.response.GetAllHouseResponseItem
import com.strawhead.ecolution.data.remote.response.PostHomeResponse
import com.strawhead.ecolution.data.remote.retrofit.ApiConfig
import com.strawhead.ecolution.ui.screen.delete.RecommendedItem
import com.strawhead.ecolution.ui.screen.home.HomeScreenViewModel
import kotlinx.coroutines.GlobalScope
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
            Text("Bookmark", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 20.dp))
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Bookmark(navigateToPlace: (image: String,
                                                title: String,
                                                price: String,
                                                address: String,
                                                description: String,
                                                sellerName: String,
                                                sellerEmail: String) -> Unit) {
    val context = LocalContext.current
    val bookmarkViewModel: BookmarkViewModel = viewModel(factory = BookMarkViewModelFactory(context))
    val state by bookmarkViewModel.state.collectAsState()
    var data by remember { mutableStateOf<List<Bookmark>?>(null) }
    bookmarkViewModel.reload()
    data = state
    Column() {
        Banner()
        if (data.isNotNull()) {
            LazyColumn(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, bottom = 20.dp, top = 20.dp)) {
                items(data!!) { place ->
                        RecommendedItem(place, modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navigateToPlace(place!!.image!!, place!!.title!!, place!!.price!!, place!!.address!!, place!!.description!!, place!!.sellerName!!, place!!.sellerEmail!!)
                            }
                            , delete = {
                                GlobalScope.launch {
                                    bookmarkViewModel.delete(bookmarkViewModel.getBookmarkImageUrl(place.image!!))
                                    bookmarkViewModel.reload()
                                }
                                for (prop in data!!) {
                                    if(prop.image == place.image) {
                                        data = data!!.filterNot { it.image == prop.image }
                                    }
                                }
                                showToast(context, "Removed from bookmarks")
                            })
                }

        }

            }
    }
}

@Composable
fun RecommendedItem(
    place: Bookmark,
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
                model = place!!.image,
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
            Image(
                painter = painterResource(id = R.drawable.baseline_cancel_24),
                contentDescription = "marker",
                modifier = Modifier.padding(start = 20.dp, end = 20.dp).clickable {
                    delete(place!!.image!!)
                }
            )
        }
    }
}

private fun showToast(ctx: Context, message: String) {
    Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
}