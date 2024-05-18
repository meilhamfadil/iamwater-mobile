package com.akuasih.app.model
import com.google.gson.annotations.SerializedName


data class NodeModel(
    @SerializedName("created_at")
    val createdAt: Any,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: Any
)