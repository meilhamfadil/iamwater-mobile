package com.akuasih.app.model

import com.google.gson.annotations.SerializedName


data class ConditionDetailModel(
    @SerializedName("data")
    val data: Data?,
    @SerializedName("pre_process")
    val preProcess: PreProcess,
    @SerializedName("calculation")
    val calculation: List<Calculation>,
)

data class Calculation(
    @SerializedName("area")
    val area: Double,
    @SerializedName("condition_id")
    val conditionId: Int,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("momen")
    val momen: Double,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("x_end")
    val xEnd: Double,
    @SerializedName("x_start")
    val xStart: Double,
    @SerializedName("y_end")
    val yEnd: Double,
    @SerializedName("y_start")
    val yStart: Double
)

data class Data(
    @SerializedName("created_at")
    val createdAt: Any,
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
    val updatedAt: Any
)

data class PreProcess(
    @SerializedName("area")
    val area: Double,
    @SerializedName("category_baik")
    val categoryBaik: Double,
    @SerializedName("category_buruk")
    val categoryBuruk: Double,
    @SerializedName("category_sangat_baik")
    val categorySangatBaik: Double,
    @SerializedName("category_sangat_buruk")
    val categorySangatBuruk: Double,
    @SerializedName("category_sedang")
    val categorySedang: Double,
    @SerializedName("condition_id")
    val conditionId: Double,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("metal_baik")
    val metalBaik: Double,
    @SerializedName("metal_buruk")
    val metalBuruk: Double,
    @SerializedName("metal_sedang")
    val metalSedang: Double,
    @SerializedName("momen")
    val momen: Double,
    @SerializedName("output")
    val output: Double,
    @SerializedName("oxygen_baik")
    val oxygenBaik: Double,
    @SerializedName("oxygen_buruk")
    val oxygenBuruk: Double,
    @SerializedName("oxygen_cukup")
    val oxygenCukup: Double,
    @SerializedName("ph_asam")
    val phAsam: Double,
    @SerializedName("ph_baik")
    val phBaik: Double,
    @SerializedName("ph_basa")
    val phBasa: Double,
    @SerializedName("tds_baik")
    val tdsBaik: Double,
    @SerializedName("tds_buruk")
    val tdsBuruk: Double,
    @SerializedName("tds_sedang")
    val tdsSedang: Double,
    @SerializedName("updated_at")
    val updatedAt: String?
)