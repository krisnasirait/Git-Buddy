package com.krisna.gitbuddy.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krisna.gitbuddy.data.model.response.search.SearchResponseItem
import com.krisna.gitbuddy.data.model.response.alluser.AllUserResponse
import com.krisna.gitbuddy.data.model.response.detail.DetailUserResponse
import com.krisna.gitbuddy.repository.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GithubViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _search = MutableLiveData<List<SearchResponseItem?>?>()
    val search: LiveData<List<SearchResponseItem?>?> = _search

    private val _allUser = MutableLiveData<AllUserResponse?>()
    val allUser: LiveData<AllUserResponse?> = _allUser

    private val _detailUser = MutableLiveData<DetailUserResponse?>()
    val detailUser: LiveData<DetailUserResponse?> = _detailUser

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> = _errorMessage


    fun getAllUsers() {
        viewModelScope.launch {
            runCatching {
                _isLoading.value = true
                withContext(Dispatchers.IO) {
                    GithubRepository().getUserList()
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
                   GithubRepository().searchUser(username)
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
                    GithubRepository().getUserDetail(username)
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


}