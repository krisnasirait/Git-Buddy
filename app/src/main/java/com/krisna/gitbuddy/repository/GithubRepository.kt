package com.krisna.gitbuddy.repository

import com.krisna.gitbuddy.data.Utility
import com.krisna.gitbuddy.data.model.response.SearchResponse
import com.krisna.gitbuddy.data.model.response.alluser.AllUserResponse
import com.krisna.gitbuddy.data.remote.ApiClient

class GithubRepository {

    suspend fun getUserList() : AllUserResponse {
        return ApiClient.instance.getUserList(Utility.authToken)
    }
    suspend fun searchUser(username: String) : SearchResponse {
        return ApiClient.instance.searchUser(username, Utility.authToken)
    }
}