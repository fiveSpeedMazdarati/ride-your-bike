package com.example.rideyourbike.data.remote.dto


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.example.rideyourbike.domain.model.ActivityDisplayItem

@Parcelize
data class ActivitiesDTOItem(
    @SerializedName("achievement_count")
    val achievementCount: Int,
    @SerializedName("athlete")
    val athlete: Athlete,
    @SerializedName("athlete_count")
    val athleteCount: Int,
    @SerializedName("average_heartrate")
    val averageHeartrate: Int,
    @SerializedName("average_speed")
    val averageSpeed: Double,
    @SerializedName("comment_count")
    val commentCount: Int,
    @SerializedName("commute")
    val commute: Boolean,
    @SerializedName("display_hide_heartrate_option")
    val displayHideHeartrateOption: Boolean,
    @SerializedName("distance")
    val distance: Double,
    @SerializedName("elapsed_time")
    val elapsedTime: Int,
    @SerializedName("elev_high")
    val elevHigh: Double,
    @SerializedName("elev_low")
    val elevLow: Double,
    @SerializedName("end_latlng")
    val endLatlng: List<Double>,
    @SerializedName("external_id")
    val externalId: String,
    @SerializedName("flagged")
    val flagged: Boolean,
    @SerializedName("from_accepted_tag")
    val fromAcceptedTag: Boolean,
    @SerializedName("gear_id")
    val gearId: Int?,
    @SerializedName("has_heartrate")
    val hasHeartrate: Boolean,
    @SerializedName("has_kudoed")
    val hasKudoed: Boolean,
    @SerializedName("heartrate_opt_out")
    val heartrateOptOut: Boolean,
    @SerializedName("id")
    val id: Long,
    @SerializedName("kudos_count")
    val kudosCount: Int,
    @SerializedName("location_city")
    val locationCity: String?,
    @SerializedName("location_country")
    val locationCountry: String?,
    @SerializedName("location_state")
    val locationState: String?,
    @SerializedName("manual")
    val manual: Boolean,
    @SerializedName("map")
    val map: Map,
    @SerializedName("max_heartrate")
    val maxHeartrate: Int,
    @SerializedName("max_speed")
    val maxSpeed: Double,
    @SerializedName("moving_time")
    val movingTime: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("photo_count")
    val photoCount: Int,
    @SerializedName("pr_count")
    val prCount: Int,
    @SerializedName("private")
    val `private`: Boolean,
    @SerializedName("resource_state")
    val resourceState: Int,
    @SerializedName("sport_type")
    val sportType: String,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("start_date_local")
    val startDateLocal: String,
    @SerializedName("start_latlng")
    val startLatlng: List<Double>,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("total_elevation_gain")
    val totalElevationGain: Double,
    @SerializedName("total_photo_count")
    val totalPhotoCount: Int,
    @SerializedName("trainer")
    val trainer: Boolean,
    @SerializedName("type")
    val type: String,
    @SerializedName("upload_id")
    val uploadId: Long,
    @SerializedName("upload_id_str")
    val uploadIdStr: String,
    @SerializedName("utc_offset")
    val utcOffset: Int,
    @SerializedName("visibility")
    val visibility: String
) : Parcelable

fun ActivitiesDTOItem.toActivityDisplayItem() : ActivityDisplayItem {
    return ActivityDisplayItem(
        name = name,
        type = type,
        distance =distance,
        averageHeartRate = averageHeartrate,
        maxHeartRate = maxHeartrate
    )
}