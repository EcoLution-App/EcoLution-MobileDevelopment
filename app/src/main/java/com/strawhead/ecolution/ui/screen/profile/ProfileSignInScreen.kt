package com.strawhead.ecolution.ui.screen.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
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
                    shape = RoundedCornerShape(bottomStart = 170.dp, bottomEnd = 170.dp)
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
                .fillMaxWidth()
                .padding(top = 100.dp, start = 50.dp, end = 50.dp)
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
                        .padding(30.dp),
                    text = stringResource(R.string.login_with),
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontSize = 30.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Google Sign-in Box
                Box(
                    modifier = Modifier
                        .clickable { onSignInClick() }
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
                        Text(text = stringResource(R.string.google))
                    }
                }
                Text(
                    text = stringResource(R.string.to_access_the_full_feature),
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
                    text = stringResource(R.string.or_continue_as_a_guest),
                    style = TextStyle(fontSize = 10.sp),
                    modifier = Modifier.padding(bottom = 50.dp)
                )
            }
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
