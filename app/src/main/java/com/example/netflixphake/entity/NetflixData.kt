package com.example.netflixphake.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class NetflixData() : Serializable {
    @SerializedName("description")
    var description: String ?= ""
    @SerializedName("sources")
    var sources: String ?= ""
    @SerializedName("subtitle")
    var subtitle: String ?= ""
    @SerializedName("thumb")
    var thumb: String ?= ""
    @SerializedName("title")
    var title: String ?= ""
}