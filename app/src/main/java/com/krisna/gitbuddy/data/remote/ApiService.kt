package com.krisna.gitbuddy.data.remote

import com.krisna.gitbuddy.data.model.response.following.FollowingResponse
import com.krisna.gitbuddy.data.model.response.search.SearchResponse
import com.krisna.gitbuddy.data.model.response.alluser.AllUserResponse
import com.krisna.gitbuddy.data.model.response.detail.DetailUserResponse
import com.krisna.gitbuddy.data.model.response.followers.FollowersResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //user list
    @GET("/users")
    suspend fun getUserList(
        @Header("Authorization") token: String
    ) : AllUserResponse

    //search user
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String,
        @Header("Authorization") token: String
    ) : SearchResponse

    //get user detail
    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): DetailUserResponse

    //get user followers
    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): FollowersResponse

    //get user following
    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): FollowingResponse

}