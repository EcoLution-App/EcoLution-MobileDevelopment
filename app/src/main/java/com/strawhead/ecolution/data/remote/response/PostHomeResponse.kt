package com.strawhead.ecolution.data.remote.response

import com.google.gson.annotations.SerializedName

data class PostHomeResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("house")
	val house: House? = null
)

data class House(

	@field:SerializedName("decscription")
	val decscription: String? = null,

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

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
