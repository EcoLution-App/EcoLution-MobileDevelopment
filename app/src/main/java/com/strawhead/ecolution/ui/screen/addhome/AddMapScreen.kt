package com.strawhead.ecolution.ui.screen.addhome

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.strawhead.ecolution.R
import com.strawhead.ecolution.ui.ViewModelFactory
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun AddMapScreen(prevAddress: String = "", prevLat: Double? = null, prevLong: Double? = null, navigateToAdd: () -> Unit) {
    var markerPosition = LatLng(-6.896666, 107.561690)
    val context = LocalContext.current
    if (prevLat != null && prevLong != null) {
        markerPosition = LatLng(prevLat, prevLong)
    } else {
        markerPosition = LatLng(-6.896666, 107.561690)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerPosition, 18f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(zoomControlsEnabled = false)
    )
    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = {

                },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_map_marker),
                    contentDescription = "marker",
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                )
            }
        }

        BannerSetLocation(modifier = Modifier.align(Alignment.TopCenter))
        if (!cameraPositionState.isMoving) {
            FooterSetLocation(place = showLocation(cameraPositionState.position.target.latitude, cameraPositionState.position.target.longitude, context),
                modifier = Modifier.align(Alignment.BottomCenter)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(20.dp)
                    ),
                navigateToAdd = {navigateToAdd()},
                lat = cameraPositionState.position.target.latitude,
                long = cameraPositionState.position.target.longitude,
                kecamatan = showLocationLocale(cameraPositionState.position.target.latitude, cameraPositionState.position.target.longitude, context))
        } else {
            FooterSetLocationLoading(
                modifier = Modifier.align(Alignment.BottomCenter)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(20.dp)
                    ))
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun PreviewMap() {
    AddMapScreen(navigateToAdd = {})
}

@Composable
fun BannerSetLocation(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Surface(
            color = Color(0xFF425A75), modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Set Location", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 20.dp))
        }
    }
}

@Composable
fun FooterSetLocation(
    modifier: Modifier = Modifier,
    place: String,
    navigateToAdd: () -> Unit,
    lat: Double,
    long: Double,
    kecamatan: String,
) {
    val context = LocalContext.current
    // scope
    val scope = rememberCoroutineScope()
    val dataStore = LocationDataStore(context)
    Box(modifier = modifier) {
        Surface(
            color = Color.White, modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                Text(text = place, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.Black, modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 20.dp))
                Button(colors = ButtonDefaults.buttonColors(Color(0xFF425A75)),
                    onClick = {
                        scope.launch {
                            dataStore.saveAddress(place)
                            dataStore.saveLat(lat.toString())
                            dataStore.saveLong(long.toString())
                            dataStore.saveKecamatan(trimKecamatan(kecamatan))
                        }
                        navigateToAdd()
                    },
                    shape = RoundedCornerShape(20),
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp).fillMaxWidth()
                ) {
                    Text("Set location")
                }
            }
        }
    }
}

@Composable
fun FooterSetLocationLoading(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Surface(
            color = Color.White, modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Loading place data...", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.Black, modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 20.dp))
        }
    }
}
fun showLocation(lat: Double, long: Double, ctx: Context): String {
    var geocoder = Geocoder(ctx, Locale.getDefault())
    val address = geocoder.getFromLocation(lat, long, 1)
    return address?.get(0)?.getAddressLine(0)!!
}

fun showLocationLocale(lat: Double, long: Double, ctx: Context): String {
    var geocoder = Geocoder(ctx, Locale.getDefault())
    val address = geocoder.getFromLocation(lat, long, 1)
    return address?.get(0)?.locality!!
}

fun trimKecamatan(kecamatan: String): String {
    var kecamatanTrimmed = kecamatan
    if(kecamatan.take(9) == "Kecamatan") {
        kecamatanTrimmed = kecamatanTrimmed.drop(10)
    }
    return kecamatanTrimmed
}