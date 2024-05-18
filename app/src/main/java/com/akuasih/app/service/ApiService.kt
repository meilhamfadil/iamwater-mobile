package com.akuasih.app.service

import com.akuasih.app.model.ConditionDetailModel
import com.akuasih.app.model.HistoryModel
import com.akuasih.app.model.NodeModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/condition/api/nodes")
    suspend fun getNodes(): List<NodeModel>

    @GET("/condition/api/histories")
    suspend fun getHistories(
        @Query("ref_id") refId: Int
    ): List<HistoryModel>

    @GET("/condition/api/calculate")
    suspend fun calculateGraph(
        @Query("ref_id") refId: Int
    )

    @GET("/condition/api/detail")
    suspend fun detailGraph(
        @Query("ref_id") refId: Int
    ): ConditionDetailModel
}