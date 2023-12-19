package com.strawhead.ecolution.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetAllHouseResponse(

	@field:SerializedName("GetAllHouseResponse")
	val getAllHouseResponse: List<GetAllHouseResponseItem>
)

data class GetAllHouseResponseItem(

	@field:SerializedName("seller")
	val seller: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("subdistrict")
	val subdistrict: String,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("email")
	val email: String
)
