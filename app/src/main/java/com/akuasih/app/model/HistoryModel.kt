package com.akuasih.app.model

import com.google.gson.annotations.SerializedName


data class HistoryModel(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("metals")
    val metals: Double,
    @SerializedName("oxygen")
    val oxygen: Double,
    @SerializedName("particles")
    val particles: Double,
    @SerializedName("ph")
    val ph: Double,
    @SerializedName("ref_id")
    val refId: Int,
    @SerializedName("source_id")
    val sourceId: Int,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("output")
    val output: Double?,
)