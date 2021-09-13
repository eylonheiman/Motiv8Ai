package com.eylonheimann.motiv8ai.repository;

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("name") val name: String,
    @SerializedName("weight") val weight: String,
    @SerializedName("bagColor") val bagColor: String
)
