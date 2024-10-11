# Ride Your Bike
This is a very simple app which fetches and displays a user's activities data from Strava, and then provides a small summary including an emoji rating based on how many of your activities have kudos.

The app implements the basics necessary to authenticate with Strava's OAuth server and retrieve some data. 
It currently does not support refreshing tokens, so the app always gets a new one when opened.

In general, I tried to use Clean code principals and a mix of MVVM and MVI patterns. when you inspect the main viewmodel, you'll see what I mean. The screen state is dictated by the MainScreenState that is emitted as a flow and collected in onCreate::setContent().
With more time, I could make this more production-ready by refactoring some things and sticking to the design patters more strictly, but within the time constraints I made the choice to get the data onto the screen first and refactor later.


## Requirements to build and run the app
- Device with a minimumSdk of 30 (Android 11)
- Internet connection
- Strava account

Launch the app, authenticate with Strava, and then you will see your activities displayed in a scrollable list.

The app currently lacks thorough testing, also as part of the time constraint.

## Tech used:

- Kotlin
- Jetpack Compose
- Retrofit2
- GSON
- Hilt
- Androidx Lifecycle components
- Coroutines and Flows
- Intents and intent filters

