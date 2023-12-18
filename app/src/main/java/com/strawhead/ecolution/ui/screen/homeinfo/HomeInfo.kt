package com.strawhead.ecolution.ui.screen.homeinfo

import android.icu.text.NumberFormat
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.strawhead.ecolution.R
import com.strawhead.ecolution.ui.screen.addhome.LocationDataStore
import com.strawhead.ecolution.ui.tempmodel.PlaceInfo
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
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
            Text("Home information", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 20.dp))
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeInfo(image: String,
             title: String,
             price: String,
             address: String,
             description: String,
             sellerName: String,
             sellerEmail: String) {
    val myIndonesianLocale = Locale("in", "ID")
    val format: NumberFormat = NumberFormat.getCurrencyInstance(myIndonesianLocale)

    val context = LocalContext.current

    var geocoder = Geocoder(context, Locale.getDefault())
    val alamat = geocoder.getFromLocationName(address, 1)
    var latitude = alamat?.get(0)?.latitude
    var longitude = alamat?.get(0)?.longitude
    var CityName = alamat?.get(0)?.subAdminArea
    Log.v("log_tag", "CityName " + CityName)

    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        sheetContainerColor = Color.White,
        scaffoldState = scaffoldState,
        sheetContent = {
            Column () {
                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp, start = 16.dp)
                )
                Text(
                    text = address,
                    fontWeight = FontWeight.ExtraLight,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 10.dp, end = 10.dp)
                )
                Text(
                    text = description,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 10.dp, end = 10.dp)
                )
                Text(
                    text = format.format(price.toInt()).dropLast(3),
                    fontWeight = FontWeight.Black,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 10.dp, end = 10.dp)
                )
                Divider(color = Color.Black, thickness = 1.dp)
            }
        },
        sheetPeekHeight = 150.dp,
        modifier = Modifier.advancedShadow(shadowBlurRadius = 5.dp)
    ) {
        Box {
            val markerPosition = LatLng(latitude!!, longitude!!)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(markerPosition, 18f)
            }
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(zoomControlsEnabled = false)
            ) {
                val place = LatLng(latitude, longitude)
                Marker(
                    position = place,
                    title = title,
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
                )
            }
            val imageDecode = URLDecoder.decode(image, StandardCharsets.UTF_8.toString())
            AsyncImage(
                model = imageDecode,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 20.dp, bottom = 170.dp)
                    .width(150.dp)
                    .height(150.dp)
                    .border(2.dp, Color.White, RectangleShape)
                    .shadow(2.dp)
                    .align(Alignment.BottomStart)
            )
            Banner()
    //        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
    //            Card (
    //                shape = MaterialTheme.shapes.medium,
    //                colors = CardDefaults.cardColors(
    //                    containerColor = Color.White,
    //                ),
    //                modifier = Modifier
    //                    .advancedShadow(shadowBlurRadius = 5.dp)
    //                    .clip(RoundedCornerShape(12.dp))
    //            ) {
    //
    //            }
    //
    //        }
        }

    }
}

//@Preview
//@Composable
//fun HomeInfoPreview(){
//    HomeInfo()
//}

fun Modifier.advancedShadow(
    color: Color = Color.Black,
    alpha: Float = 1f,
    cornersRadius: Dp = 0.dp,
    shadowBlurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = drawBehind {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparentColor = color.copy(alpha = 0f).toArgb()

    drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowBlurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
    }
}
