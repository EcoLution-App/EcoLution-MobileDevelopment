package com.strawhead.ecolution.ui.screen.homeinfo

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.NumberFormat
import android.location.Geocoder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.yml.charts.axis.AxisData
import co.yml.charts.common.extensions.isNotNull
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.strawhead.ecolution.R
import com.strawhead.ecolution.data.local.database.Bookmark
import com.strawhead.ecolution.ui.screen.addhome.LocationDataStore
import com.strawhead.ecolution.ui.screen.bookmark.BookMarkViewModelFactory
import com.strawhead.ecolution.ui.screen.bookmark.BookmarkViewModel
import com.strawhead.ecolution.ui.screen.home.HomeScreenViewModel
import com.strawhead.ecolution.ui.tempmodel.PlaceInfo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Locale

//@Composable
//fun Banner(
//    modifier: Modifier = Modifier,
//) {
//    val bookmarkViewModel = viewModel(modelClass = BookmarkViewModel::class.java)
//
//}
@SuppressLint("CoroutineCreationDuringComposition")
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
    val homeInfoViewModel = viewModel(modelClass = HomeInfoViewModel::class.java)
    val state by homeInfoViewModel.state.collectAsState()
    val loading by homeInfoViewModel.loading.collectAsState()
    val context = LocalContext.current
    val bookmarkViewModel: BookmarkViewModel = viewModel(factory = BookMarkViewModelFactory(context))
    var isFavorite by remember { mutableStateOf(false) }
    var isFinishedLookingDatabase by remember { mutableStateOf(false) }
    var geocoder = Geocoder(context, Locale.getDefault())
    val alamat = geocoder.getFromLocationName(address, 1)
    var latitude = alamat?.get(0)?.latitude
    var longitude = alamat?.get(0)?.longitude
    var CityName = alamat?.get(0)?.subAdminArea
    Log.v("log_tag", "CityName " + CityName!!.uppercase())
    homeInfoViewModel.reload(CityName!!.uppercase())

    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        sheetContainerColor = Color.White,
        scaffoldState = scaffoldState,
        sheetContent = {
            Column (modifier = Modifier.verticalScroll(rememberScrollState())) {
                Divider(color = Color(0xffeae5e7), thickness = 1.dp)
                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp, start = 16.dp)
                )
                Row(modifier = Modifier.padding(start = 16.dp, bottom = 10.dp, end = 10.dp)) {
                    Image(painter = painterResource(R.drawable.ic_map_marker), contentDescription = null,)
                    Text(
                        text = address,
                        fontWeight = FontWeight.ExtraLight,
                    )
                }
                Text(
                    text = description,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 10.dp, end = 10.dp)
                )
                Text(
                    text = format.format(price.toInt()).dropLast(3),
                    fontWeight = FontWeight.Black,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 10.dp, end = 10.dp)
                )
                Divider(color = Color(0xffeae5e7), thickness = 1.dp)
                Text(
                    text = "Seller Contacts",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 10.dp, end = 10.dp, top = 10.dp)
                )
                Row {
                    Text(
                        text = sellerName,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(start = 16.dp, bottom = 10.dp, end = 10.dp)
                            .width(150.dp)
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = sellerEmail,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(start = 16.dp, bottom = 10.dp, end = 10.dp)
                    )

                }
                Divider(color = Color(0xffeae5e7), thickness = 1.dp)
                if(!loading) {
                    if(state.isNotNull() && state!!.combinedValuesY.isNotNull()) {
                        Log.v("hasil ml", state!!.combinedValuesY.toString())
                        Text(
                            text = "Prediksi jumlah kejadian banjir per tahun",
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, bottom = 10.dp, end = 10.dp, top = 20.dp)
                        )
                        val steps = 5
                        val pointsData: List<Point> =
                            listOf(
                                Point(2012f, state!!.combinedValuesY!!.get(0)!!),
                                Point(2013f, state!!.combinedValuesY!!.get(1)!!),
                                Point(2014f, state!!.combinedValuesY!!.get(2)!!),
                                Point(2015f, state!!.combinedValuesY!!.get(3)!!),
                                Point(2016f, state!!.combinedValuesY!!.get(4)!!),
                                Point(2017f, state!!.combinedValuesY!!.get(5)!!),
                                Point(2018f, state!!.combinedValuesY!!.get(6)!!),
                                Point(2019f, state!!.combinedValuesY!!.get(7)!!),
                                Point(2020f, state!!.combinedValuesY!!.get(8)!!),
                                Point(2021f, state!!.combinedValuesY!!.get(9)!!),
                                Point(2022f, state!!.combinedValuesY!!.get(10)!!),
                                Point(2023f, state!!.combinedValuesY!!.get(11)!!),
                                Point(2024f, state!!.combinedValuesY!!.get(12)!!),
                                Point(2025f, state!!.combinedValuesY!!.get(13)!!),
                            )

                        val xAxisData = AxisData.Builder()
                            .axisStepSize(100.dp)
                            .backgroundColor(Color.White)
                            .steps(pointsData.size - 1)
                            .labelData { i -> (i+2012).toString() }
                            .labelAndAxisLinePadding(15.dp)
                            .build()

                        val yAxisData = AxisData.Builder()
                            .steps(steps)
                            .backgroundColor(Color.White)
                            .labelAndAxisLinePadding(20.dp)
                            .labelData { i ->
                                val yScale = state!!.combinedValuesY!!.max() / steps.toFloat()
                                (i * yScale).toString().take(3)
                            }.build()

                        val lineChartData = LineChartData(
                            linePlotData = LinePlotData(
                                lines = listOf(
                                    Line(
                                        dataPoints = pointsData,
                                        LineStyle(),
                                        IntersectionPoint(),
                                        SelectionHighlightPoint(),
                                        ShadowUnderLine(),
                                        SelectionHighlightPopUp()
                                    )
                                ),
                            ),
                            xAxisData = xAxisData,
                            yAxisData = yAxisData,
                            gridLines = GridLines(),
                            backgroundColor = Color.White
                        )

                        LineChart(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            lineChartData = lineChartData
                        )

                    }
                }



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
            Box() {
                Surface(
                    color = Color(0xFF425A75), modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row {
                        Text("Home information", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 20.dp))
                        Spacer(Modifier.weight(1f))

                        GlobalScope.launch {
                            var bookmarkList = bookmarkViewModel.getAllBookmarks()
                            Log.d("bookmarkaa", bookmarkList.toString())
                            if (bookmarkList.isNotNull()) {
                                for (prop in bookmarkList) {
                                    if (prop.image == image) {
                                        isFavorite = true
                                    }
                                }
                            }
                            isFinishedLookingDatabase = true
                        }
                        if (isFinishedLookingDatabase) {
                            if (isFavorite) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_heart_full),
                                    contentDescription = "marker",
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 16.dp)
                                        .width(35.dp)
                                        .height(35.dp)
                                        .clickable {
                                            GlobalScope.launch {
                                                bookmarkViewModel.delete(bookmarkViewModel.getBookmarkImageUrl(image))
                                            }
                                            showToast(context, "Removed from bookmark")
                                            isFavorite = false
                                        }
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_heart_outline),
                                    contentDescription = "marker",
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 16.dp)
                                        .width(35.dp)
                                        .height(35.dp)
                                        .clickable {
                                            var bookmark = Bookmark()
                                            bookmark.let {
                                                it.sellerName = sellerName
                                                it.title = title
                                                it.sellerEmail = sellerEmail
                                                it.price = price
                                                it.image = image
                                                it.description = description
                                                it.address= address
                                            }
                                            bookmarkViewModel.insert(bookmark)
                                            showToast(context, "Added to bookmark")
                                            isFavorite = true
                                        }
                                )
                            }

                        }
                    }
                }
            }

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

private fun showToast(ctx: Context, message: String) {
    Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
}
