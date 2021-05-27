package com.footballclubs.app.networking.model

data class Clubs(
    val club: List<Club>
)

data class Club(
    val id: String,
    val name: String,
    val country: String,
    val value: Int,
    val image: String,
    val european_titles: Int,
    val stadium: Stadium,
    val location: Location
)

data class Stadium(
    val size: Int,
    val name: String
)

data class Location(
    val lat: Float,
    val lng: Float
)