package com.strawhead.ecolution.ui.screen.homeinfo

import android.icu.text.NumberFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.strawhead.ecolution.ui.screen.addhome.LocationDataStore
import com.strawhead.ecolution.ui.tempmodel.PlaceInfo
import java.util.Locale

@Composable
fun HomeInfo() {
    val myIndonesianLocale = Locale("in", "ID")
    val format: NumberFormat = NumberFormat.getCurrencyInstance(myIndonesianLocale)

    val context = LocalContext.current
    val dataStore = HomeInfoDataStore(context)

    val savedTitle = dataStore.getTitle.collectAsState(initial = "")
    val savedPrice = dataStore.getPrice.collectAsState(initial = "")
    val savedAddress = dataStore.getAddress.collectAsState(initial = "")
    val savedKecamatan = dataStore.getKecamatan.collectAsState(initial = "")
    val savedDesc = dataStore.getDescription.collectAsState(initial = "")
    val savedSellerName = dataStore.getSellerName.collectAsState(initial = "")
    val savedSellerEmail = dataStore.getSellerEmail.collectAsState(initial = "")

    Column {
        Text(savedTitle.value!!)
        if (savedPrice.value!! != "") {
            Text(format.format(savedPrice.value!!.toInt()).dropLast(3))
        }
        Text(savedAddress.value!!)
        Text(savedKecamatan.value!!)
        Text(savedDesc.value!!)
        Text(savedSellerName.value!!)
        Text(savedSellerEmail.value!!)
    }
}