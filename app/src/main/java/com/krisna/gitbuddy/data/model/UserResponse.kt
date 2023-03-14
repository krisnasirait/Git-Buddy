package com.krisna.gitbuddy.data.model


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<User>,
    @SerializedName("total_count")
    val totalCount: Int
)