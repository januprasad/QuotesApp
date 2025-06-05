package com.toyota.disney.repo.model

import com.google.gson.annotations.SerializedName

data class DisneyCharResponse(
    @SerializedName("data")
    val data: List<Character>,
    val info: Info? = null
)