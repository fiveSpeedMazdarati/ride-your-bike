package com.example.rideyourbike.data.remote.dto


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class TokenExchangeResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("athlete")
    val athlete: AthleteX,
    @SerializedName("expires_at")
    val expiresAt: Int,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("token_type")
    val tokenType: String
) : Parcelable