package com.example.rideyourbike

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
                    LaunchedEffect("dataFetch") {
                        CoroutineScope(Dispatchers.IO).launch {
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StravaContent(data: List<ActivitiesDTOItem>) {
    Box(Modifier.fillMaxSize()) {
        Column (Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(.8f)) {

                stickyHeader {
                    MainScreenListHeader("Name", "Type", "Distance")
                }
                data.forEach {
                    item {
                        MainScreenDisplayItem(it)
                    }
                }
                item {

                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.2f)
                    .requiredHeight(150.dp)
            ) {
                MainScreenSummary(data)
            }
        }
    }
}

@Composable
fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun MainScreenDisplayItem(item: ActivitiesDTOItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(text = item.name, modifier = Modifier.weight(.5f, true))
        Text(
            text = item.type, modifier = Modifier
                .padding(start = 16.dp)
                .weight(.3f, true)
        )
        Text(
            text = item.distance.toString(),
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(.2f, true)
        )
    }
}

@Composable
fun MainScreenListHeader(text1: String, text2: String, text3: String) {
    Box(Modifier.background(color = Color.White)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .padding(top = 24.dp, bottom = 16.dp)
        ) {
            Text(text = text1, modifier = Modifier.weight(.5f))
            Text(
                text = text2, modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(.25f)
            )
            Text(
                text = text3, modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(.25f)
            )
        }
    }
}

@Composable
fun MainScreenSummary(
    data: List<ActivitiesDTOItem>,
    viewModel: LoginScreenViewModel = viewModel<LoginScreenViewModel>()
) {
    val summaryData = viewModel.calculateSummaryData(data)

    Box(Modifier.background(color = Color.LightGray).fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp, start = 36.dp)
                .requiredHeight(150.dp)
        ) {
            Text(
                text = "Summary",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )
            Text(text = "Activities: ${summaryData.countOfActivities}")
            Text(text = "Average Distance: ${summaryData.averageDistance} meters")
            Text("${summaryData.countOfKudos} of your activities have Kudos    ${summaryData.emoji}")
        }
    }
}