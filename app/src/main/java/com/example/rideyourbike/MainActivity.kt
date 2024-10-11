package com.example.rideyourbike

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rideyourbike.common.Constants
import com.example.rideyourbike.data.remote.dto.ActivitiesDTOItem
import com.example.rideyourbike.data.repository.TokenExchangeRequestData
import com.example.rideyourbike.presentation.main_screen.MainScreenState
import com.example.rideyourbike.presentation.theme.RideyourbikeTheme
import com.example.rideyourbike.presentation.viewmodel.LoginScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var accessCode: String = ""

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RideyourbikeTheme {

                val viewModel = viewModel<LoginScreenViewModel>()
                val state by viewModel.state.collectAsState(MainScreenState())

                val appLinkIntent: Intent = intent
                var appLinkAction: String? = appLinkIntent.action
                val appLinkData: Uri? = appLinkIntent.data

                // determine if it is the default launch or if it was captured from a URL
                if (appLinkAction == "android.intent.action.VIEW" && appLinkData?.getQueryParameter(
                        "code"
                    ) != null
                ) {

                    // when authenticated, call API and get data to display on the screen
                    appLinkData.getQueryParameter("code")?.let {
                        accessCode = it
                    }

                    Log.d("LOGIN", "action: $appLinkAction")
                    Log.d("LOGIN", "code: $accessCode")

                    viewModel.setLoggedIn(true)

                    // solves a bug where the app would repeatedly try to fetch the Strava data on error
                    appLinkIntent.setAction(null)
                    appLinkAction = null
                }

                if (state.isLoggedIn && !state.fetchedStravaData) {
                    runBlocking {
                        viewModel.getAllTheData(
                            TokenExchangeRequestData(
                                clientSecret = Constants.CLIENT_SECRET,
                                clientId = Constants.CLIENT_ID,
                                code = accessCode,
                                grantType = Constants.INITIAL_AUTHENTICATION_TYPE
                            )
                        )
                    }

                }

                when {
                    state.data.isNotEmpty() -> {
                        StravaContent(state.data)
                    }

                    state.displayLoginButton -> {
                        LoginContent()
                    }

                    state.isLoading -> {
                        LoadingContent()
                    }

                    state.isError -> {
                        ErrorStateContent()
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginContent() {
    val context = LocalContext.current
    Scaffold(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Let's go biking!",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 48.dp)
            )
            Button(
                onClick = {
                    val intentUri =
                        Uri.parse("https://www.strava.com/oauth/mobile/authorize")
                            .buildUpon()
                            .appendQueryParameter("client_id", Constants.CLIENT_ID.toString())
                            .appendQueryParameter(
                                "redirect_uri",
                                "https://www.lukebusch.com/app"
                            )
                            .appendQueryParameter("response_type", "code")
                            .appendQueryParameter("approval_prompt", "auto")
                            .appendQueryParameter("scope", "activity:read")
                            .build()

                    Intent(Intent.ACTION_VIEW, intentUri).also {
                        context.startActivity(it)
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(
                        start = 16.dp,
                        top = 350.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            ) {
                Text(text = "Log in with Strava")
            }
        }
    }
}

@Composable
fun ErrorStateContent() {
    val context = LocalContext.current
    Column() {
        Text(
            text = "There was a problem getting your information from Strava.",
            color = Color.Red,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(top = 250.dp)
                .padding(start = 24.dp, end = 24.dp)
                .align(Alignment.CenterHorizontally)
        )

    }
}

@Composable
fun StravaContent(data: List<ActivitiesDTOItem>) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Got strava content!", modifier = Modifier.align(Alignment.Center))
    }
    Log.d("LOGIN", data.toString())
}

@Composable
fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("Getting your information...", modifier = Modifier.align(Alignment.Center))
    }
}