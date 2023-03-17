package com.krisna.gitbuddy.repository

import com.krisna.gitbuddy.data.Utility
import com.krisna.gitbuddy.data.model.response.FollowingResponse
import com.krisna.gitbuddy.data.model.response.search.SearchResponse
import com.krisna.gitbuddy.data.model.response.alluser.AllUserResponse
import com.krisna.gitbuddy.data.model.response.detail.DetailUserResponse
import com.krisna.gitbuddy.data.model.response.followers.FollowersResponse
import com.krisna.gitbuddy.data.remote.ApiClient

class GithubRepository {

    suspend fun getUserList() : AllUserResponse {
        return ApiClient.instance.getUserList(Utility.authToken)
    }
    suspend fun searchUser(username: String) : SearchResponse {
        return ApiClient.instance.searchUser(username, Utility.authToken)
    }
    suspend fun getUserDetail(username: String) : DetailUserResponse {
        return ApiClient.instance.getUser(username, Utility.authToken)
    }

    suspend fun getUserFollowers(username: String) : FollowersResponse {
        return ApiClient.instance.getUserFollowers(username, Utility.authToken)
    }

    suspend fun getUserFollowing(username: String) : FollowingResponse {
        return ApiClient.instance.getUserFollowing(username, Utility.authToken)
    }
}