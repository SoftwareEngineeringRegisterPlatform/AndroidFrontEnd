package cn.hospital.registerplatform.data.dto

import com.google.gson.annotations.SerializedName

enum class LoadType(private val value: String) {
    @SerializedName("all")
    ALL("all"),

    @SerializedName("count")
    COUNT("count"),

    @SerializedName("page")
    PAGE("page");

    override fun toString(): String {
        return value
    }
}
