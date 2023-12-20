package com.strawhead.ecolution

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Delete
import com.google.android.gms.auth.api.identity.Identity
import com.strawhead.ecolution.model.BottomBarItem
import com.strawhead.ecolution.signin.GoogleUiAuthClient
import com.strawhead.ecolution.ui.navigation.Screen
import com.strawhead.ecolution.ui.screen.addhome.AddMapScreen
import com.strawhead.ecolution.ui.screen.addhome.AddScreen
import com.strawhead.ecolution.ui.screen.bookmark.Bookmark
import com.strawhead.ecolution.ui.screen.delete.DeleteScreen
import com.strawhead.ecolution.ui.screen.home.HomeScreen
import com.strawhead.ecolution.ui.screen.homeinfo.HomeInfo
import com.strawhead.ecolution.ui.screen.profile.ProfileScreen
import com.strawhead.ecolution.ui.screen.profile.ProfileSignInScreen
import com.strawhead.ecolution.ui.screen.profile.ProfileViewModel
import com.strawhead.ecolution.ui.theme.EcoLutionTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun EcoLutionApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    val context = LocalContext.current

    val googleAuthUiClient by lazy {
        GoogleUiAuthClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if ((currentRoute?.take(3) != "add" && currentRoute?.take(8) != "infohome")) {
                BottomBar(navController)
            } },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navigateToPlace = { image, title, price, address, description, sellerName, sellerEmail ->
                    val encodedUrl = URLEncoder.encode(image, StandardCharsets.UTF_8.toString())
                    navController.navigate("infohome/$encodedUrl/$title/$price/$address/$description/$sellerName/$sellerEmail")
                })
            }

            composable("infohome/{image}/{title}/{price}/{address}/{description}/{sellerName}/{sellerEmail}",
                arguments = listOf(navArgument("image") {
                    type = NavType.StringType
                }, navArgument("title") {
                    type = NavType.StringType
                }, navArgument("price") {
                    type = NavType.StringType
                }, navArgument("address") {
                    type = NavType.StringType
                }, navArgument("description") {
                    type = NavType.StringType
                }, navArgument("sellerName") {
                    type = NavType.StringType
                }, navArgument("sellerEmail") {
                    type = NavType.StringType
                },
                    )) {
                HomeInfo(image = it.arguments?.getString("image")!!,
                    title = it.arguments?.getString("title")!!,
                    price = it.arguments?.getString("price")!!,
                    address = it.arguments?.getString("address")!!,
                    description = it.arguments?.getString("description")!!,
                    sellerName = it.arguments?.getString("sellerName")!!,
                    sellerEmail = it.arguments?.getString("sellerEmail")!!)
            }

            composable(Screen.Bookmark.route) {
                Bookmark()
            }

            composable(Screen.Profile.route) {

                val viewModel = viewModel<ProfileViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

                LaunchedEffect(key1 = Unit) {
                    if(googleAuthUiClient.getSignedInUser() != null) {
                        navController.navigate("profile") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                }

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if(result.resultCode == ComponentActivity.RESULT_OK) {
                            GlobalScope.launch(Dispatchers.IO) {
                                val signInResult = googleAuthUiClient.signInWithIntent(
                                    intent = result.data ?: return@launch
                                )
                                viewModel.onSignInResult(signInResult)
                            }
                        }
                    }
                )

                LaunchedEffect(key1 = state.isSignInSuccessful) {
                    if(state.isSignInSuccessful) {
                        Toast.makeText(
                            context,
                            "Sign in successful",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.navigate("profile")
                        viewModel.resetState()
                    }
                }

                ProfileSignInScreen(
                    state = state,
                    onSignInClick = {
                        GlobalScope.launch(Dispatchers.IO) {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    }
                )
            }

            composable("profile") {
                ProfileScreen(
                    userData = googleAuthUiClient.getSignedInUser(),
                    onSignOut = {
                        GlobalScope.launch(Dispatchers.Main) {
                            googleAuthUiClient.signOut()
                            Toast.makeText(
                                context,
                                "Signed out",
                                Toast.LENGTH_LONG
                            ).show()
                            navController.popBackStack()
                        }
                    },
                    navigateToPlace = {navController.navigate("Add") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    } },
                    navigateToSales = {navController.navigate("My Sales") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    } }
                )
            }

            composable("My Sales") {
                DeleteScreen(userData = googleAuthUiClient.getSignedInUser(), showToast = { showToast(context, it) },
                    navigateToPlace = { image, title, price, address, description, sellerName, sellerEmail ->
                        val encodedUrl = URLEncoder.encode(image, StandardCharsets.UTF_8.toString())
                        navController.navigate("infohome/$encodedUrl/$title/$price/$address/$description/$sellerName/$sellerEmail")
                    })
            }

            composable(route = Screen.Add.route) {
                if(googleAuthUiClient.getSignedInUser() == null) {
                    navController.navigate(Screen.Profile.route)
                } else {
                    AddScreen(navigateToMap = {Latitude, Longitude ->
                        if (Latitude == null && Longitude == null) {
                            navController.navigate("add")
                        } else {
                            navController.navigate("add/" + Latitude.toString() + "/" + Longitude.toString())
                        } },
                        navigateBack = {navController.popBackStack()},
                        userData = googleAuthUiClient.getSignedInUser(),
                        showToast = {msg -> showToast(context, msg)})
                }
            }

            composable("add/{prevLat}/{prevLong}",
                arguments = listOf(navArgument("prevLat") {
                    type = NavType.StringType
                }, navArgument("prevLong") {
                    type = NavType.StringType
                })) {
                    AddMapScreen(navigateToAdd = {navController.popBackStack()}, prevLat = it.arguments?.getString("prevLat")!!.toDouble(), prevLong = it.arguments?.getString("prevLong")!!.toDouble())
            }

            composable("add") {
                AddMapScreen(navigateToAdd = {navController.popBackStack()})
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier.shadow(
        elevation = 10.dp,
        shape = RoundedCornerShape(20.dp)
    )
) {
    val context = LocalContext.current
    val googleAuthUiClient by lazy {
        GoogleUiAuthClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }
    NavigationBar(
        containerColor = Color.White,
        modifier = modifier,
        contentColor = Color(0xFF425A75)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            BottomBarItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home
            ),
            BottomBarItem(
                title = "Bookmark",
                icon = Icons.Default.Star
            ),
            BottomBarItem(
                title = "Add",
                icon = Icons.Default.AddCircle
            ),
            BottomBarItem(
                title = "My Sales",
                icon = Icons.Default.List
            ),
            BottomBarItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle
            ),
        )
        navigationItems.map {
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0xff9ba8b6),
                ),
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.title
                    )
                },
                label = {
                    Text(it.title)
                },
                selected = if (currentRoute != null) {
                            it.title.uppercase().take(3) == currentRoute.uppercase().take(3)
                        } else {
                            it.title.take(3) == currentRoute.toString().take(3)
                               },
                onClick = {
                    if (it.title == "Add" || it.title == "My Sales") {
                        if(googleAuthUiClient.getSignedInUser() == null) {
                            navController.navigate(Screen.Profile.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        } else {
                            navController.navigate(it.title) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        }
                    } else {
                        navController.navigate(it.title) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun EcoLutionAppPreview() {
    EcoLutionTheme {
        EcoLutionApp()
    }
}

private fun showToast(ctx: Context, message: String) {
    Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
}