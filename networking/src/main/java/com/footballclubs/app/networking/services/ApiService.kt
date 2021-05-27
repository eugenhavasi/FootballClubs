package com.footballclubs.app.networking.services

import com.footballclubs.app.networking.model.AuthToken
import com.footballclubs.app.networking.model.Club
import com.footballclubs.app.networking.model.Clubs
import com.squareup.moshi.Moshi
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST

const val API_VERSION = "v1"


interface ApiService {

    @POST("/$API_VERSION/login")
    fun login(): Single<AuthToken>

    @GET("clubs.json")
    fun getClubs(): Call<List<Club>>
}
