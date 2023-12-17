package com.strawhead.ecolution.ui.screen.home

import android.icu.text.NumberFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.strawhead.ecolution.ui.components.Search
import com.strawhead.ecolution.ui.tempmodel.PlaceInfo
import com.strawhead.ecolution.ui.tempmodel.dummyPlaceInfo
import java.util.Locale

@Composable
fun HomeScreen(navigateToPlace: (image: String,
                                 title: String,
                                 price: String,
                                 address: String,
                                 description: String,
                                 sellerName: String,
                                 sellerEmail: String) -> Unit) {
    Column() {
        Banner()
        Text(
            text = "Recommended near you",
            modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp),
            fontWeight = FontWeight.Bold,
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
        ) {
            items(dummyPlaceInfo) { place ->
                NearYouItem(place, Modifier.shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(8.dp))
                    .clickable {
                        navigateToPlace(place.image, place.title, place.price, place.address, place.description, place.sellerName, place.sellerEmail)
                    }
                )
            }
        }
        Text(
            text = "Based on your preference",
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
            fontWeight = FontWeight.Bold,
        )
//        RecommendedColumn(dummyPlaceInfo, Modifier.padding(start = 15.dp, end = 15.dp, bottom = 20.dp), navigateToPlace = {navigateToPlace()})
        LazyColumn(modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 20.dp)) {
            items(dummyPlaceInfo) { place ->
                RecommendedItem(place, modifier = Modifier
                    .clickable {
                        navigateToPlace(place.image, place.title, place.price, place.address, place.description, place.sellerName, place.sellerEmail)
                    })
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
    place: PlaceInfo,
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
                model = place.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(105.dp)
            )
            Column () {
                Text(
                    text = place.title,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp, start = 16.dp)
                )
                Text(
                    text = place.address.take(26)+"...",
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 10.dp, end = 10.dp)
                )
                Text(
                    text = format.format(place.price.toInt()).dropLast(3),
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
    place: PlaceInfo,
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
                model = place.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = place.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = place.address.substring(0, 13) + "...",
                    maxLines = 1,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Light,
                )
                Text(
                    text = format.format(place.price.toInt()).dropLast(3),
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