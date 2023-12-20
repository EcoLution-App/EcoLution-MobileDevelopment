package com.strawhead.ecolution.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetFloodPredictionResponse(

	@field:SerializedName("combined_values_x")
	val combinedValuesX: List<Float?>? = null,

	@field:SerializedName("combined_values_y")
	val combinedValuesY: List<Float>? = null
)
