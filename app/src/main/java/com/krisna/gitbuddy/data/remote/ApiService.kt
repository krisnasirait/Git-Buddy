package com.krisna.gitbuddy.data.remote

import com.krisna.gitbuddy.data.model.Search
import com.krisna.gitbuddy.data.model.response.FollowResponse
import com.krisna.gitbuddy.data.model.response.ProfileResponse
import com.krisna.gitbuddy.data.model.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String,
        @Header("Authorization") token: String
    ) : SearchResponse

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): ProfileResponse

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): List<FollowResponse>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): List<FollowResponse>

}