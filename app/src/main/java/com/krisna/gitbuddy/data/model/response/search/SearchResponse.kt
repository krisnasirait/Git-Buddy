package com.krisna.gitbuddy.data.model.response.search


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val searchResponseItems: List<SearchResponseItem>,
    @SerializedName("total_count")
    val totalCount: Int
)