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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rideyourbike.data.remote.dto.ActivitiesDTO
import com.example.rideyourbike.presentation.theme.RideyourbikeTheme
import com.example.rideyourbike.presentation.viewmodel.LoginScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var code: String = ""

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RideyourbikeTheme {

                val viewModel = viewModel<LoginScreenViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

                val appLinkIntent: Intent = intent
                val appLinkAction: String? = appLinkIntent.action
                val appLinkData: Uri? = appLinkIntent.data
                code = ""
                // determine if it is the default launch or if it was captured from a URL
                if (appLinkAction != null && appLinkData?.getQueryParameter("code") != null) {

                    // when authenticated, call API and get data to display on the screen
                    appLinkData.getQueryParameter("code")?.let {
                        code = it
                    }

                    Log.d("LOGIN", "action: $appLinkAction")
                    Log.d("LOGIN", "code: $code")

                    viewModel.getStravaData(code)
                    // solves a bug where the app would repeatedly try to fetch the Strava data on error
                    appLinkIntent.setAction(null)
                }

                when {
                    state.isError -> ErrorStateContent(code, viewModel)
                    state.isLoading -> LoadingContent()
                    state.displayLoginButton -> LoginContent()
                    state.data?.items?.isNotEmpty() == true -> StravaContent(state.data)
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
                            .appendQueryParameter("client_id", "136135")
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
fun ErrorStateContent(code: String, viewModel: LoginScreenViewModel) {
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
        Button(onClick = {
            viewModel.getStravaData(code)
        },
            modifier =
            Modifier.align(Alignment.CenterHorizontally).padding(top = 48.dp)) {
            Text(text = "Try again")
        }
    }
}

@Composable
fun StravaContent(data: ActivitiesDTO?) {
    Log.d("LOGIN", data.toString())
}

@Composable
fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("Getting your information...", modifier = Modifier.align(Alignment.Center))
    }
}