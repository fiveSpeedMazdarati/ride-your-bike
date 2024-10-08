package com.example.rideyourbike

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rideyourbike.presentation.theme.RideyourbikeTheme
import com.example.rideyourbike.presentation.viewmodel.LoginScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: LoginScreenViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // read the intent that started the activity
        val appLinkIntent: Intent = intent
        val appLinkAction: String? = appLinkIntent.action
        val appLinkData: Uri? = appLinkIntent.data

        // determine if it is the default launch or if it was captured from a URL
        if (appLinkAction != Intent.ACTION_MAIN && appLinkData?.getQueryParameter("code") != null ){
            // when authenticated, call API and get data to display on the next screen
            val code = appLinkData.getQueryParameter("code")
            Log.d("LOGIN", "action: $appLinkAction")
            Log.d("LOGIN","code: $code")

            val data = viewModel.getStravaData(code!!)

            Log.d("LOGIN", "Retrieved Data: $data")


        }
        setContent {
            RideyourbikeTheme {

                Scaffold() {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Let's go biking!",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 24.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                .padding(top = 48.dp)
                        )

if ( true ) {
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
                                    startActivity(it)
                                }


                            },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(start = 16.dp, top = 350.dp, end = 16.dp, bottom = 16.dp)
                        ) {
                            Text(text = "Log in with Strava")
                        }
                    }
                        else {

                        }
                    }
                }
            }
        }

    }
    }

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}