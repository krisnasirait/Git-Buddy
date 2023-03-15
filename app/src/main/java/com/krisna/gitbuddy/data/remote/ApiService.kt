package com.krisna.gitbuddy.data.remote

import com.krisna.gitbuddy.data.model.response.FollowResponse
import com.krisna.gitbuddy.data.model.response.ProfileResponse
import com.krisna.gitbuddy.data.model.response.SearchResponse
import com.krisna.gitbuddy.data.model.response.alluser.AllUserResponse
import com.krisna.gitbuddy.data.model.response.alluser.AllUserResponseItem
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
    ): ProfileResponse

    //get user followers
    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): FollowResponse

    //get user following
    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): FollowResponse

}