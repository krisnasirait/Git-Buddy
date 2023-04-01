package com.krisna.gitbuddy.data.repository

import com.krisna.gitbuddy.BuildConfig
import com.krisna.gitbuddy.data.entity.FavoriteUser
import com.krisna.gitbuddy.data.model.response.alluser.AllUserResponse
import com.krisna.gitbuddy.data.model.response.detail.DetailUserResponse
import com.krisna.gitbuddy.data.model.response.followers.FollowersResponse
import com.krisna.gitbuddy.data.model.response.following.FollowingResponse
import com.krisna.gitbuddy.data.model.response.search.SearchResponse
import com.krisna.gitbuddy.data.remote.ApiService
import com.krisna.gitbuddy.data.repository.dao.FavoriteUserDao

class GithubRepository(
    private val apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDao
) {

    private val authToken = BuildConfig.AUTH_TOKEN_KEY

    suspend fun getUserList() : AllUserResponse {
        return apiService.getUserList(authToken)
    }
    suspend fun searchUser(username: String) : SearchResponse {
        return apiService.searchUser(username, authToken)
    }
    suspend fun getUserDetail(username: String) : DetailUserResponse {
        return apiService.getUser(username, authToken)
    }

    suspend fun getUserFollowers(username: String) : FollowersResponse {
        return apiService.getUserFollowers(username, authToken)
    }

    suspend fun getUserFollowing(username: String) : FollowingResponse {
        return apiService.getUserFollowing(username, authToken)
    }

    suspend fun getAllFavoriteUser() : List<FavoriteUser> {
        return favoriteUserDao.getAllUser()
    }

    suspend fun insertFavoriteUser(favoriteUser: FavoriteUser) {
        favoriteUserDao.insertUser(favoriteUser)
    }

    suspend fun deleteFavoriteUser(favoriteUser: FavoriteUser) {
        favoriteUserDao.deleteUser(favoriteUser)
    }
}