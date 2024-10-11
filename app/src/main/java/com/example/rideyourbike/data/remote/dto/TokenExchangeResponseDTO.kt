package com.example.rideyourbike.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TokenExchangeResponseDTO(
    val tokenType: String,
    val expiresAt: Int,
    val expiresIn: Int,
    val refreshToken: String,
    val accessToken: String,
) : Parcelable
