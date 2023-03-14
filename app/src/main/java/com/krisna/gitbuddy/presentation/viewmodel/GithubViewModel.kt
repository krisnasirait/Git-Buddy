package com.krisna.gitbuddy.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krisna.gitbuddy.data.Utility
import com.krisna.gitbuddy.data.model.Search
import com.krisna.gitbuddy.data.remote.ApiService
import com.krisna.gitbuddy.repository.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GithubViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _users = MutableLiveData<List<Search?>?>()
    val users: LiveData<List<Search?>?> = _users

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> = _errorMessage

    fun searchUser(username: String) {
        viewModelScope.launch {
           runCatching {
               _isLoading.value = true
               withContext(Dispatchers.IO) {
                   GithubRepository().searchUser(username)
               }
           }.onSuccess { data ->
               withContext(Dispatchers.Main) {
                   _users.value = data.searches
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