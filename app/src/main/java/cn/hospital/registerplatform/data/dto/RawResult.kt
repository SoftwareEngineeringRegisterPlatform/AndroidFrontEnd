package cn.hospital.registerplatform.data.dto

import com.google.gson.annotations.SerializedName

data class RawResult<T>(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("content")
    val content: T
)
