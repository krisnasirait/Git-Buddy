package com.krisna.gitbuddy.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krisna.gitbuddy.data.entity.FavoriteUser
import com.krisna.gitbuddy.data.model.response.following.FollowingResponse
import com.krisna.gitbuddy.data.model.response.alluser.AllUserResponse
import com.krisna.gitbuddy.data.model.response.detail.DetailUserResponse
import com.krisna.gitbuddy.data.model.response.followers.FollowersResponse
import com.krisna.gitbuddy.data.model.response.search.SearchResponseItem
import com.krisna.gitbuddy.data.repository.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GithubViewModel(
    private val repository: GithubRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _search = MutableLiveData<List<SearchResponseItem?>?>()
    val search: LiveData<List<SearchResponseItem?>?> = _search

    private val _allUser = MutableLiveData<AllUserResponse?>()
    val allUser: LiveData<AllUserResponse?> = _allUser

    private val _detailUser = MutableLiveData<DetailUserResponse?>()
    val detailUser: LiveData<DetailUserResponse?> = _detailUser

    private val _userFollowers = MutableLiveData<FollowersResponse?>()
    val userFollowers: LiveData<FollowersResponse?> = _userFollowers

    private val _userFollowing = MutableLiveData<FollowingResponse?>()
    val userFollowing: LiveData<FollowingResponse?> = _userFollowing

    private val _userFavorite = MutableLiveData<List<FavoriteUser?>?>()
    val userFavorite: LiveData<List<FavoriteUser?>?> = _userFavorite

    private val _clickedUsername = MutableLiveData<String>()
    val clickedUsername: LiveData<String>
        get() = _clickedUsername


    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String>
        get() = _successMessage

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getAllUsers() {
        viewModelScope.launch {
            runCatching {
                _isLoading.value = true
                withContext(Dispatchers.IO) {
                    repository.getUserList()
                }
            }.onSuccess { userlist ->
                withContext(Dispatchers.Main) {
                    _allUser.value = userlist
                    _isLoading.value = false
                }
            }.onFailure { error ->
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Error found : ${error.message}"
                    _isLoading.value = false
                }
            }
        }
    }

    fun searchUser(username: String) {
        viewModelScope.launch {
           runCatching {
               _isLoading.value = true
               withContext(Dispatchers.IO) {
                   repository.searchUser(username)
               }
           }.onSuccess { data ->
               withContext(Dispatchers.Main) {
                   _search.value = data.searchResponseItems
                   _isLoading.value = false
               }
           }.onFailure { error ->
               withContext(Dispatchers.Main) {
                   _errorMessage.value = "Error found : ${error.message}"
                   _isLoading.value = false
               }
           }
        }
    }

    fun getUserDetail(username: String) {
        viewModelScope.launch {
            runCatching {
                _isLoading.value = true
                withContext(Dispatchers.IO) {
                    repository.getUserDetail(username)
                }
            }.onSuccess { detailUser ->
                withContext(Dispatchers.Main) {
                    _detailUser.value = detailUser
                    _isLoading.value = false
                }
            }.onFailure { error ->
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Error found : ${error.message}"
                    _isLoading.value = false
                }
            }
        }
    }

    fun getUserFollowers(username: String) {
        viewModelScope.launch {
            runCatching {
                _isLoading.value = true
                withContext(Dispatchers.IO) {
                    repository.getUserFollowers(username)
                }
            }.onSuccess { userFollowers ->
                withContext(Dispatchers.Main) {
                    _userFollowers.value = userFollowers
                    Log.d("savedUsername", "followersInVM : ${userFollowers.size}")
                    _isLoading.value = false
                }
            }.onFailure { error ->
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Error found : ${error.message}"
                    _isLoading.value = false
                }
            }
        }
    }

    fun getUserFollowing(username: String) {
        viewModelScope.launch {
            runCatching {
                _isLoading.value = true
                withContext(Dispatchers.IO) {
                    repository.getUserFollowing(username)
                }
            }.onSuccess { userFollowing ->
                withContext(Dispatchers.Main) {
                    _userFollowing.value = userFollowing
                    Log.d("savedUsername", "followingInVM : ${userFollowing.size}")
                    _isLoading.value = false
                }
            }.onFailure { error ->
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Error found : ${error.message}"
                    _isLoading.value = false
                }
            }
        }
    }

    fun setClickedUsername(username: String) {
        _clickedUsername.value = username
        Log.d("savedUsername", "inViewModel : $username")
    }

    fun getAllFavoriteUser() {
        viewModelScope.launch {
            runCatching {
                _isLoading.value = true
                withContext(Dispatchers.IO) {
                    repository.getAllFavoriteUser()
                }
            }.onSuccess { favoriteUser ->
                withContext(Dispatchers.Main) {
                    _userFavorite.value = favoriteUser
                    _isLoading.value = false
                }
            }.onFailure { error ->
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Error found : ${error.message}"
                    _isLoading.value = false
                }
            }
        }
    }

    fun insertFavoriteUser(favoriteUser: FavoriteUser) {
        viewModelScope.launch {
            runCatching {
                _isLoading.value = true
                withContext(Dispatchers.IO) {
                    repository.insertFavoriteUser(favoriteUser)
                }
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    _successMessage.value = "User added to favorite"
                    _isLoading.value = false
                }
            }.onFailure { error ->
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Error found : ${error.message}"
                    _isLoading.value = false
                }
            }
        }
    }

    fun deleteFavoriteUser(favoriteUser: FavoriteUser) {
        viewModelScope.launch {
            runCatching {
                _isLoading.value = true
                withContext(Dispatchers.IO) {
                    repository.deleteFavoriteUser(favoriteUser)
                }
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    _successMessage.value = "User deleted from favorite"
                    _isLoading.value = false
                }
            }.onFailure { error ->
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Error found : ${error.message}"
                    _isLoading.value = false
                }
            }
        }
    }


}