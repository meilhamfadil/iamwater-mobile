package com.akuasih.app.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceHelper {

    private const val BASE_URL = "http://akuasih.my.id/"

    private fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService = getInstance().create(ApiService::class.java)
}