package com.krisna.gitbuddy.data.model


import com.google.gson.annotations.SerializedName
import com.krisna.gitbuddy.data.model.response.UserResponse

data class User(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<UserResponse>,
    @SerializedName("total_count")
    val totalCount: Int
)