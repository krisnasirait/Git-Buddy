package com.krisna.gitbuddy.data.model.response


import com.google.gson.annotations.SerializedName
import com.krisna.gitbuddy.data.model.Search

data class SearchResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val searches: List<Search>,
    @SerializedName("total_count")
    val totalCount: Int
)