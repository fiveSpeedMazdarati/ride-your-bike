package com.example.rideyourbike.data.remote.dto


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class AthleteX(
    @SerializedName("badge_type_id")
    val badgeTypeId: Int,
    @SerializedName("bio")
    val bio: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("follower")
    val follower: String,
    @SerializedName("friend")
    val friend: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("premium")
    val premium: Boolean,
    @SerializedName("profile")
    val profile: String,
    @SerializedName("profile_medium")
    val profileMedium: String,
    @SerializedName("resource_state")
    val resourceState: Int,
    @SerializedName("sex")
    val sex: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("summit")
    val summit: Boolean,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("weight")
    val weight: String
) : Parcelable