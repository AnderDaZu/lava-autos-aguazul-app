package com.anderdadev.lavaautosaguazul.resposes

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val title: String,
    @SerializedName("service") val service: String,
    @SerializedName("type") val type: String,
    @SerializedName("url_image") val url: String,
    @SerializedName("extract") val extract: String,
)

data class PostListResponse(
    @SerializedName("response") val posts: List<PostResponse>
)