package com.strawhead.ecolution.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strawhead.ecolution.R
import com.strawhead.ecolution.ui.components.Search
import com.strawhead.ecolution.ui.tempmodel.PlaceInfo
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import com.strawhead.ecolution.ui.tempmodel.dummyPlaceInfo

@Composable
fun HomeScreen() {
    Column() {
        Banner()
        Text(
            text = "Recommended near you",
            modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp),
            fontWeight = FontWeight.Bold,
        )
        NearYouRow(dummyPlaceInfo, Modifier.padding(top = 20.dp, bottom = 20.dp))
        Text(
            text = "Based on your preference",
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
            fontWeight = FontWeight.Bold,
        )
        RecommendedColumn(dummyPlaceInfo, Modifier.padding(start = 15.dp, end = 15.dp, bottom = 20.dp))
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
            Image(
                painter = painterResource(place.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(105.dp)
            )
            Column () {
                Text(
                    text = place.placeName,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp, start = 16.dp)
                )
                Text(
                    text = place.placeAddress,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 10.dp, end = 10.dp)
                )
                Text(
                    text = place.distance.toString() + " km",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 10.dp, end = 10.dp)
                )
            }
        }
    }
}
@Composable
fun NearYouRow(
    listPlace: List<PlaceInfo>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(listPlace) { place ->
            NearYouItem(place, Modifier.shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(8.dp)
            ))
        }
    }
}

@Composable
fun RecommendedColumn(
    listPlace: List<PlaceInfo>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(listPlace) { place ->
            RecommendedItem(place)
        }
    }
}
@Composable
fun NearYouItem(
    place: PlaceInfo,
    modifier: Modifier = Modifier,
) {
    Card (
        modifier = modifier.width(140.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    ){
        Column {
            Image(
                painter = painterResource(place.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = place.placeName,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = place.placeAddress.substring(0, 13) + "...",
                    maxLines = 1,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Light,
                )
                Text(
                    text = place.distance.toString() + " km",
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun PreviewHome() {
    HomeScreen()
}

@Composable
@Preview(showBackground = true)
fun NearYouItemPreview() {
    NearYouItem(
        place = PlaceInfo(R.drawable.photo_rumah, "FNAF Pizzeria", "Backroom street number 420", 6.9),
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun RecommendedItemPreview() {
    RecommendedItem(
        place = PlaceInfo(R.drawable.photo_rumah, "FNAF Pizzeria", "Backroom street number 420", 6.9),
        modifier = Modifier.padding(8.dp)
    )
}