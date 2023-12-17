package com.strawhead.ecolution.ui.tempmodel

import com.strawhead.ecolution.R

data class PlaceInfo(
    val id: Int,
    val image: String,
    val title: String,
    val price: String,
    val address: String,
    val kecamatan: String,
    val description: String,
    val sellerName: String,
    val sellerEmail: String
)

val dummyPlaceInfo = listOf(
    PlaceInfo(1, "https://images.pexels.com/photos/106399/pexels-photo-106399.jpeg", "Rumah lantai 2", "500000000", "Jl. Karya No.47, Sukaraja, Kec. Cicendo, Kota Bandung, Jawa Barat 40184, Indonesia", "Cicendo", "Rumah tingkat dua siap pakai dengan 3 kamar tidur, 2 kamar mandi, dapur, ruang keluarga, dan ruang tamu", "Muhammad Rizalul Fiqri Syah Dani", "fortugtss@gmail.com"),
    PlaceInfo(2, "https://images.pexels.com/photos/106399/pexels-photo-106399.jpeg", "Rumah lantai 2", "500000000", "Jl. Karya No.47, Sukaraja, Kec. Cicendo, Kota Bandung, Jawa Barat 40184, Indonesia", "Cicendo", "Rumah tingkat dua siap pakai dengan 3 kamar tidur, 2 kamar mandi, dapur, ruang keluarga, dan ruang tamu", "Muhammad Rizalul Fiqri Syah Dani", "fortugtss@gmail.com"),
    PlaceInfo(3, "https://images.pexels.com/photos/106399/pexels-photo-106399.jpeg", "Rumah lantai 2", "500000000", "Jl. Karya No.47, Sukaraja, Kec. Cicendo, Kota Bandung, Jawa Barat 40184, Indonesia", "Cicendo", "Rumah tingkat dua siap pakai dengan 3 kamar tidur, 2 kamar mandi, dapur, ruang keluarga, dan ruang tamu", "Muhammad Rizalul Fiqri Syah Dani", "fortugtss@gmail.com"),
    PlaceInfo(4, "https://images.pexels.com/photos/106399/pexels-photo-106399.jpeg", "Rumah lantai 2", "500000000", "Jl. Karya No.47, Sukaraja, Kec. Cicendo, Kota Bandung, Jawa Barat 40184, Indonesia", "Cicendo", "Rumah tingkat dua siap pakai dengan 3 kamar tidur, 2 kamar mandi, dapur, ruang keluarga, dan ruang tamu", "Muhammad Rizalul Fiqri Syah Dani", "fortugtss@gmail.com"),
    PlaceInfo(5, "https://images.pexels.com/photos/106399/pexels-photo-106399.jpeg", "Rumah lantai 2", "500000000", "Jl. Karya No.47, Sukaraja, Kec. Cicendo, Kota Bandung, Jawa Barat 40184, Indonesia", "Cicendo", "Rumah tingkat dua siap pakai dengan 3 kamar tidur, 2 kamar mandi, dapur, ruang keluarga, dan ruang tamu", "Muhammad Rizalul Fiqri Syah Dani", "fortugtss@gmail.com"),
    PlaceInfo(6, "https://images.pexels.com/photos/106399/pexels-photo-106399.jpeg", "Rumah lantai 2", "500000000", "Jl. Karya No.47, Sukaraja, Kec. Cicendo, Kota Bandung, Jawa Barat 40184, Indonesia", "Cicendo", "Rumah tingkat dua siap pakai dengan 3 kamar tidur, 2 kamar mandi, dapur, ruang keluarga, dan ruang tamu", "Muhammad Rizalul Fiqri Syah Dani", "fortugtss@gmail.com"),
)
