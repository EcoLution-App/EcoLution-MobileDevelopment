package com.strawhead.ecolution.ui.screen.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.strawhead.ecolution.R
import com.strawhead.ecolution.signin.UserData
import com.strawhead.ecolution.ui.screen.home.HomeScreenViewModel
import com.strawhead.ecolution.ui.screen.home.ReloadDataStore
import com.strawhead.ecolution.ui.theme.gray
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProfileScreen(userData: UserData?, onSignOut: () -> Unit) {
    val placeOwnedViewModel = viewModel(modelClass = PlaceOwnedViewModel::class.java)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val dataStore = ReloadDataStoreForAcc(context)
    val reload = dataStore.getReload.collectAsState(initial = "true")
    if (reload.value == "true") {
        placeOwnedViewModel.reload()
        scope.launch {
            dataStore.saveReload("false")
        }
    }
    val stateData by placeOwnedViewModel.stateData.collectAsState()
    var jumlah by remember { mutableStateOf(0) }
    jumlah = stateData.count{it.email == userData?.email}

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = gray)
    ) {
        Box(
            modifier = Modifier
                .height(230.dp)
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.bluesky),
                    shape = RoundedCornerShape(bottomStart = 170.dp, bottomEnd = 170.dp)
                )
                .padding(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (userData?.profilePictureUrl != null) {
                    AsyncImage(
                        model = userData.profilePictureUrl,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                val username = userData?.username
                val email = userData?.email
                if (username != null) {
                    Text(
                        text = username,
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .widthIn(max = 300.dp)
                            .padding(bottom = 2.dp),
                        style = TextStyle(
                            lineHeight = 36.sp
                        )
                    )

                    if (email != null) {
                        Text(
                            text = email,
                            textAlign = TextAlign.Center,
                            fontSize = 15.sp,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .widthIn(max = 300.dp)
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .height(230.dp)
                .width(225.dp)
                .offset(y = -1f.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(color = colorResource(id = R.color.white))
                .align(Alignment.Center)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .width(100.dp),
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = null
                )
                Text(
                    text = jumlah.toString() + " Place Posted",
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        // Add place
        Box(
            modifier = Modifier
                .padding(top = 300.dp)
                .height(50.dp)
                .width(225.dp)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(15.dp))
                .background(color = colorResource(id = R.color.white))

        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.baseline_add_circle_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(R.string.add_place))
            }
        }
        // Add place
        Box(
            modifier = Modifier
                .padding(top = 420.dp)
                .height(50.dp)
                .width(225.dp)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(15.dp))
                .background(color = colorResource(id = R.color.white))
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.baseline_edit_place_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(R.string.edit_place))
            }
        }
        Button(
            onClick = onSignOut,
            colors = ButtonDefaults.buttonColors(Color(0xFF425A75)),
            modifier = Modifier
                .height(50.dp)
                .width(225.dp)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(15.dp))
                .background(color = Color(0xFF425A75))
        ) {
            Text(text = "Sign out")
        }
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    val sampleUserData = UserData(
        userId = "123",
        profilePictureUrl = "Image",
        username = "EcoLution",
        email = "ecolution@example.com",
    )

    ProfileScreen(
        userData = sampleUserData,
        onSignOut = {}
    )
}


//        Button(onClick = onSignOut) {
//            Text(text = "Sign out")
//        }
//    }
//}