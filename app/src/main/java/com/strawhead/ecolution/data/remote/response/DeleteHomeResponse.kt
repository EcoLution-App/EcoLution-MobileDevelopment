package com.strawhead.ecolution.data.remote.response

import com.google.gson.annotations.SerializedName

data class DeleteHomeResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("house")
	val house: House

)
