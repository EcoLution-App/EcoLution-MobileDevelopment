package com.strawhead.ecolution.ui.screen.addhome

import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.location.Geocoder
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toFile
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import com.strawhead.ecolution.R
import com.strawhead.ecolution.data.remote.response.PostHomeResponse
import com.strawhead.ecolution.data.remote.retrofit.ApiConfig
import com.strawhead.ecolution.signin.UserData
import com.strawhead.ecolution.ui.ViewModelFactory
import com.strawhead.ecolution.ui.screen.home.ReloadDataStore
import com.strawhead.ecolution.ui.screen.profile.ReloadDataStoreForAcc
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.Date
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
            Text("Add Home", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 20.dp))
        }
    }
}

@Composable
fun AddScreen(userData: UserData?,
              navigateToMap: (Double?, Double?) -> Unit,
              viewModel: AddHomeViewModel = viewModel(
                  factory = ViewModelFactory()
              ),
              navigateBack: () -> Unit,
              showToast:(String) -> Unit
) {
    val namas by viewModel.nama.collectAsStateWithLifecycle()
    val hargas by viewModel.harga.collectAsStateWithLifecycle()
    val deskripsis by viewModel.deskripsi.collectAsStateWithLifecycle()
    val gambar by viewModel.image.collectAsStateWithLifecycle()
    val gambarFile by viewModel.imageFile.collectAsStateWithLifecycle()
    var nama by remember { mutableStateOf(namas) }
    var harga by remember { mutableStateOf(hargas) }
    var deskripsi by remember { mutableStateOf(deskripsis) }
    val openDialog = remember { mutableStateOf(false) }
    var submitButtonEnable by remember { mutableStateOf(true) }
    var buttonTitle by remember { mutableStateOf("Submit") }
    val sellerName = userData!!.username
    val sellerEmail = userData!!.email
    val context = LocalContext.current
    // scope
    val scope = rememberCoroutineScope()
    val dataStore = LocationDataStore(context)
    val reloadDataStore = ReloadDataStore(context)
    val reloadDataStoreAcc = ReloadDataStoreForAcc(context)

    // get saved email
    val savedAddress = dataStore.getAddress.collectAsState(initial = "")
    val savedLat = dataStore.getLang.collectAsState(initial = "")
    val savedLong = dataStore.getLong.collectAsState(initial = "")
    val kecamatan = dataStore.getKecamatan.collectAsState(initial = "")
    var lokasi by remember { mutableStateOf("") }

    var imageUri by remember {
        mutableStateOf<Uri?>(gambar)
    }

    var imageFile by remember {
        mutableStateOf<File?>(gambarFile)
    }

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        viewModel.changeImageValue(uri)
    }

    BackHandler {
        openDialog.value = true
    }

    if(openDialog.value) {
        AlertDialog(
            onDismissRequest = {openDialog.value = false },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch {
                        dataStore.saveAddress("")
                        dataStore.saveLat("")
                        dataStore.saveKecamatan("")
                        dataStore.saveLong("")
                    }
                    navigateBack()
                })
                { Text(text = "OK") }
            },
            dismissButton = {
                TextButton(onClick = {openDialog.value = false})
                { Text(text = "Cancel") }
            },
            title = { Text(text = "Cancel add home") },
            text = { Text(text = "Are you sure you want to cancel adding your home?") }
        )
    }

//        .navigate(Screen.Profile.route)
    Column (verticalArrangement = Arrangement.Center
    ){
        Banner(modifier = Modifier.padding(bottom = 10.dp))
        imageUri?.let { uri ->
            // Display the selected image
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = null,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
            )
        }
        if(imageUri == null) {
            Image(painter = painterResource(R.drawable.insert_image),
                contentDescription = null,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth())
        }
        Button(colors = ButtonDefaults.buttonColors(Color(0xFF425A75)),
            onClick = { launcher.launch("image/*") },
            shape = RoundedCornerShape(20),
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                .fillMaxWidth()
        ) {
            Text("Set Image")
        }
        OutlinedTextField(
            value = nama,
            label = { Text("Nama rumah") },
            maxLines = 1,
            onValueChange = {newInput ->
                nama = newInput
                viewModel.changeNamaValue(newInput)
            },
            modifier = Modifier
                .padding(start = 20.dp, top = 5.dp, bottom = 5.dp, end = 20.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = harga,
            label = { Text("Harga rumah") },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {newInput ->
                harga = newInput
                viewModel.changeHargaValue(newInput)
            },
            modifier = Modifier
                .padding(start = 20.dp, top = 5.dp, bottom = 5.dp, end = 20.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = savedAddress.value!!,
            readOnly = true,
            label = {
                Row{
                    Image(painter = painterResource(R.drawable.ic_map_marker), contentDescription = null,)
                    Text("Lokasi rumah")
                }
                    },
            onValueChange = {newInput ->
                lokasi = newInput
                viewModel.changeLokasiValue(newInput)
            },
            maxLines = 1,
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                if (savedAddress.value!! != "") {
//                                    var geocoder = Geocoder(context, Locale.getDefault())
//                                    val coord = geocoder.getFromLocationName(savedAddress.value!!, 1)
                                    navigateToMap(savedLat.value!!.toDouble(), savedLong.value!!.toDouble())
                                } else {
                                    navigateToMap(null, null)
                                }
                            }
                        }
                    }
                },
            modifier = Modifier
                .padding(start = 20.dp, top = 5.dp, bottom = 5.dp, end = 20.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = deskripsi,
            label = { Text("Deskripsi rumah") },
            onValueChange = {newInput ->
                deskripsi = newInput
                viewModel.changeDeskripsiValue(newInput)
            },
            maxLines = 2,
            modifier = Modifier
                .defaultMinSize(minHeight = 65.dp)
                .padding(start = 20.dp, top = 5.dp, bottom = 10.dp, end = 20.dp)
                .fillMaxWidth()
        )
        if ((nama != "" && harga != "" && savedAddress.value!! != "" && kecamatan.value!! != "" && deskripsi != "" && imageUri != null)) {
            Button(colors = ButtonDefaults.buttonColors(Color(0xFF425A75)),
                enabled = submitButtonEnable,
                onClick = {
                    if(savedAddress.value!! != "") {
                        Log.d("Data rumah -> Nama tempat : ", nama)
                        Log.d("Data rumah -> Harga : ", harga)
                        Log.d("Data rumah -> Alamat panjang : ", savedAddress.value!!)
                        Log.d("Data rumah -> Kecamatan : ", kecamatan.value!!)
                        Log.d("Data rumah -> Deskripsi : ", deskripsi)
                        Log.d("Data rumah -> Nama penjual : ", sellerName!!)
                        Log.d("Data rumah -> Email penjual : ", sellerEmail!!)
                        submitButtonEnable = false
                        buttonTitle = "Uploading"
                        imageUri?.let { uri ->
                            val imageFile = uriToFile(uri, context)
                            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                            val titleReq = nama.toRequestBody("text/plain".toMediaType())
                            val priceReq = harga.toRequestBody("text/plain".toMediaType())
                            val descriptionReq = deskripsi.toRequestBody("text/plain".toMediaType())
                            val sellerReq = sellerName!!.toRequestBody("text/plain".toMediaType())
                            val emailReq = sellerEmail!!.toRequestBody("text/plain".toMediaType())
                            val addressReq = savedAddress.value!!.toRequestBody("text/plain".toMediaType())
                            val subdistrictReq = kecamatan.value!!.toRequestBody("text/plain".toMediaType())
                            val multipartBody = MultipartBody.Part.createFormData(
                                "image",
                                imageFile!!.name,
                                requestImageFile
                            )
                            scope.launch {
                                try {
                                    val apiService = ApiConfig.getApiService()
                                    val successResponse = apiService.uploadHouseData(titleReq, priceReq, descriptionReq, sellerReq, emailReq, addressReq, subdistrictReq, multipartBody)
                                    Log.d("status upload data", successResponse.message!!)
                                    if(successResponse.message!! == "House added successfully") {
                                        scope.launch {
                                            dataStore.saveAddress("")
                                            dataStore.saveLat("")
                                            dataStore.saveKecamatan("")
                                            dataStore.saveLong("")
                                        }
                                        reloadDataStoreAcc.saveReload("true")
                                        reloadDataStore.saveReload("true")
                                        navigateBack()
                                        showToast(successResponse.message!!)
                                    }
                                    submitButtonEnable = true
                                    buttonTitle = "Submit"
                                } catch (e: HttpException) {
                                    val errorBody = e.response()?.errorBody()?.string()
                                    val errorResponse = Gson().fromJson(errorBody, PostHomeResponse::class.java)
                                    Log.d("status upload data", errorResponse.message!!)
                                    showToast(errorResponse.message!!)
                                    submitButtonEnable = true
                                    buttonTitle = "Submit"
                                }
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(20),
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(buttonTitle)
            }
        } else {
            Button(colors = ButtonDefaults.buttonColors(Color(0xFF425A75)),
                enabled = false, onClick = {},
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20)) {
                Text("All form data need to be completed before submitting")
            }
        }
    }
}

//@Preview(showBackground = true, device = Devices.PIXEL_4)
//@Composable
//fun PreviewAdd() {
//    AddScreen(navigateToMap = {Double, Doubles -> }, navigateBack = {}, userData = null)
//}

//private fun uploadHomeData(title: String, price: String, description: String, seller: String, email: String, address: String, subdistrict: String, image: File, ctx: Context) {
//    val requestImageFile = image.asRequestBody("image/jpeg".toMediaType())
//    val titleReq = title.toRequestBody("text/plain".toMediaType())
//    val priceReq = price.toRequestBody("text/plain".toMediaType())
//    val descriptionReq = description.toRequestBody("text/plain".toMediaType())
//    val sellerReq = seller.toRequestBody("text/plain".toMediaType())
//    val emailReq = email.toRequestBody("text/plain".toMediaType())
//    val addressReq = address.toRequestBody("text/plain".toMediaType())
//    val subdistrictReq = subdistrict.toRequestBody("text/plain".toMediaType())
//    val multipartBody = MultipartBody.Part.createFormData(
//        "imageUrl",
//        image.name,
//        requestImageFile
//    )
//    GlobalScope.launch {
//        try {
//            val apiService = ApiConfig.getApiService()
//            val successResponse = apiService.uploadHouseData(titleReq, priceReq, descriptionReq, sellerReq, emailReq, addressReq, subdistrictReq, multipartBody)
//            showToast(ctx, successResponse.message!!)
//        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorResponse = Gson().fromJson(errorBody, PostHomeResponse::class.java)
//            showToast(ctx, errorResponse.message!!)
//        }
//    }
//}

private fun createCustomTempFile(context: Context): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val filesDir = context.externalCacheDir
    return File.createTempFile(timeStamp, ".jpg", filesDir)
}
private fun uriToFile(imageUri: Uri, context: Context): File {
    val myFile = createCustomTempFile(context)
    val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
    val outputStream = FileOutputStream(myFile)
    val buffer = ByteArray(1024)
    var length: Int
    while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
    outputStream.close()
    inputStream.close()
    return myFile
}

private fun showToast(ctx: Context, message: String) {
    Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
}