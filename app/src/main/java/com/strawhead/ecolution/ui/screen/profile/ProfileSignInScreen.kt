package com.strawhead.ecolution.ui.screen.profile

import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strawhead.ecolution.R
import com.strawhead.ecolution.signin.SignInState
import com.strawhead.ecolution.ui.theme.gray


@Composable
fun ProfileSignInScreen(
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = gray)
    ) {
        // Blue Sky Circle
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .height(230.dp)
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.bluesky),
                    shape = RoundedCornerShape(bottomStart = 150.dp, bottomEnd = 150.dp)
                )
                .padding(20.dp)
        ) {
            // Icon
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                tint = colorResource(id = R.color.green),
                modifier = Modifier
                    .size(275.dp)
                    .align(Alignment.BottomCenter)
            )
        }

        // White Box
        Box(
            modifier = Modifier
                .padding(top = 50.dp)
                .height(250.dp)
                .width(225.dp)
                .offset(y = -0.3f.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(color = colorResource(id = R.color.white))
                .align(Alignment.Center)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(20.dp),
                    text = "Login with",
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontSize = 30.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Google Sign-in Box
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .width(130.dp)
                        .shadow(2.dp, shape = RectangleShape)
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_google),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Google")
                    }
                }
                Text(
                    text = "To access the full feature",
                    style = TextStyle(fontSize = 15.sp),
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                Divider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .padding(top = 15.dp, bottom = 15.dp)
                )
                Text(
                    text = "Or Continue as a guest",
                    style = TextStyle(fontSize = 10.sp)
                )
            }
        }// Sign-in button
        Button(
            onClick = onSignInClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(15.dp))
                .height(60.dp)
                .width(300.dp)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(text = "Sign in")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileSignInScreenPreview() {
    val defaultSignInState = SignInState()
    ProfileSignInScreen(
        state = defaultSignInState,
        onSignInClick = {}
    )
}