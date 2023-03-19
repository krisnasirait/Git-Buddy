package com.krisna.gitbuddy.data.repository

import com.krisna.gitbuddy.BuildConfig
import com.krisna.gitbuddy.data.model.response.FollowingResponse
import com.krisna.gitbuddy.data.model.response.alluser.AllUserResponse
import com.krisna.gitbuddy.data.model.response.detail.DetailUserResponse
import com.krisna.gitbuddy.data.model.response.followers.FollowersResponse
import com.krisna.gitbuddy.data.model.response.search.SearchResponse
import com.krisna.gitbuddy.data.remote.ApiClient

class GithubRepository {

    private val authToken = BuildConfig.AUTH_TOKEN_KEY

    suspend fun getUserList() : AllUserResponse {
        return ApiClient.instance.getUserList(authToken)
    }
    suspend fun searchUser(username: String) : SearchResponse {
        return ApiClient.instance.searchUser(username, authToken)
    }
    suspend fun getUserDetail(username: String) : DetailUserResponse {
        return ApiClient.instance.getUser(username, authToken)
    }

    suspend fun getUserFollowers(username: String) : FollowersResponse {
        return ApiClient.instance.getUserFollowers(username, authToken)
    }

    suspend fun getUserFollowing(username: String) : FollowingResponse {
        return ApiClient.instance.getUserFollowing(username, authToken)
    }
}