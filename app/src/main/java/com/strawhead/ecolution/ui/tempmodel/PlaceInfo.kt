package com.strawhead.ecolution.ui.tempmodel

import com.strawhead.ecolution.R

data class PlaceInfo(
    val id: Int,
    val image: Int,
    val title: String,
    val price: String,
    val address: String,
    val kecamatan: String,
    val description: String,
    val sellerName: String,
    val sellerEmail: String
)

val dummyPlaceInfo = listOf(
    PlaceInfo(1, R.drawable.photo_rumah, "Rumah lantai 2", "500000000", "Jl. Karya No.47, Sukaraja, Kec. Cicendo, Kota Bandung, Jawa Barat 40184, Indonesia", "Cicendo", "Rumah tingkat dua siap pakai dengan 3 kamar tidur, 2 kamar mandi, dapur, ruang keluarga, dan ruang tamu", "Muhammad Rizalul Fiqri Syah Dani", "fortugtss@gmail.com"),
    PlaceInfo(2, R.drawable.photo_rumah, "Rumah lantai 2", "500000000", "Jl. Karya No.47, Sukaraja, Kec. Cicendo, Kota Bandung, Jawa Barat 40184, Indonesia", "Cicendo", "Rumah tingkat dua siap pakai dengan 3 kamar tidur, 2 kamar mandi, dapur, ruang keluarga, dan ruang tamu", "Muhammad Rizalul Fiqri Syah Dani", "fortugtss@gmail.com"),
    PlaceInfo(3, R.drawable.photo_rumah, "Rumah lantai 2", "500000000", "Jl. Karya No.47, Sukaraja, Kec. Cicendo, Kota Bandung, Jawa Barat 40184, Indonesia", "Cicendo", "Rumah tingkat dua siap pakai dengan 3 kamar tidur, 2 kamar mandi, dapur, ruang keluarga, dan ruang tamu", "Muhammad Rizalul Fiqri Syah Dani", "fortugtss@gmail.com"),
    PlaceInfo(4, R.drawable.photo_rumah, "Rumah lantai 2", "500000000", "Jl. Karya No.47, Sukaraja, Kec. Cicendo, Kota Bandung, Jawa Barat 40184, Indonesia", "Cicendo", "Rumah tingkat dua siap pakai dengan 3 kamar tidur, 2 kamar mandi, dapur, ruang keluarga, dan ruang tamu", "Muhammad Rizalul Fiqri Syah Dani", "fortugtss@gmail.com"),
    PlaceInfo(5, R.drawable.photo_rumah, "Rumah lantai 2", "500000000", "Jl. Karya No.47, Sukaraja, Kec. Cicendo, Kota Bandung, Jawa Barat 40184, Indonesia", "Cicendo", "Rumah tingkat dua siap pakai dengan 3 kamar tidur, 2 kamar mandi, dapur, ruang keluarga, dan ruang tamu", "Muhammad Rizalul Fiqri Syah Dani", "fortugtss@gmail.com"),
    PlaceInfo(6, R.drawable.photo_rumah, "Rumah lantai 2", "500000000", "Jl. Karya No.47, Sukaraja, Kec. Cicendo, Kota Bandung, Jawa Barat 40184, Indonesia", "Cicendo", "Rumah tingkat dua siap pakai dengan 3 kamar tidur, 2 kamar mandi, dapur, ruang keluarga, dan ruang tamu", "Muhammad Rizalul Fiqri Syah Dani", "fortugtss@gmail.com"),
)
