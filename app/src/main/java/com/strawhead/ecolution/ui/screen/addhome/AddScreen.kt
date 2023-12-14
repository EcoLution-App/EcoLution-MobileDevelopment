package com.strawhead.ecolution.ui.screen.addhome

import android.content.Intent
import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberAsyncImagePainter

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
fun AddScreen() {
    val context = LocalContext.current
    var nama by remember { mutableStateOf("") }
    var harga by remember { mutableStateOf("") }
    var lokasi by remember { mutableStateOf("") }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column (verticalArrangement = Arrangement.Center
    ){
        Banner(modifier = Modifier.padding(bottom = 10.dp))
        imageUri?.let { uri ->
            // Display the selected image
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = null,
                modifier = Modifier.height(200.dp).fillMaxWidth()
            )
        }
        Button(colors = ButtonDefaults.buttonColors(Color(0xFF425A75)),
            onClick = { launcher.launch("image/*") },
            shape = RoundedCornerShape(20),
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp).fillMaxWidth()
        ) {
            Text("Set Image")
        }
        OutlinedTextField(
            value = nama,
            label = { Text("Nama rumah") },
            onValueChange = {newInput ->
                nama = newInput
            },
            modifier = Modifier
                .padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 20.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = harga,
            label = { Text("Harga rumah") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {newInput ->
                harga = newInput
            },
            modifier = Modifier
                .padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 20.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = lokasi,
            readOnly = true,
            label = { Text("Lokasi rumah") },
            onValueChange = {newInput ->
                lokasi = newInput
            },
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                Intent(context, LocationSetActivity::class.java).also {
                                    startActivity(context, it, null)
                                }
                            }
                        }
                    }
                },
            modifier = Modifier
                .padding(start = 20.dp, top = 10.dp, bottom = 20.dp, end = 20.dp)
                .fillMaxWidth()
        )
        Button(colors = ButtonDefaults.buttonColors(Color(0xFF425A75)),
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(20),
            modifier = Modifier.padding(start = 20.dp, end = 20.dp).fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun PreviewAdd() {
    AddScreen()
}