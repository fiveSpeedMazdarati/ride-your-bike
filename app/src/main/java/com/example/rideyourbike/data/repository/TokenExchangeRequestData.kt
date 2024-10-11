package com.example.rideyourbike.data.repository

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TokenExchangeRequestData(
    val clientId: Int,
    val clientSecret: String,
    val code: String,
    val grantType: String,
) : Parcelable
