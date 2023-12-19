package com.strawhead.ecolution.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetAllHouseResponse(

	@field:SerializedName("GetAllHouseResponse")
	val getAllHouseResponse: List<GetAllHouseResponseItem?>? = null
)

data class GetAllHouseResponseItem(

	@field:SerializedName("seller")
	val seller: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("subdistrict")
	val subdistrict: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
