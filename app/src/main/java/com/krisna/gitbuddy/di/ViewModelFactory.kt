package com.krisna.gitbuddy.di

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.krisna.gitbuddy.BuildConfig
import com.krisna.gitbuddy.data.remote.ApiService
import com.krisna.gitbuddy.data.repository.GithubRepository
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: GithubRepository,
    private val context: Context
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(GithubViewModel::class.java) -> {
                GithubViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        private const val BASE_URL = "https://api.github.com/"

        private val client = OkHttpClient.Builder()
            .apply {
                if(BuildConfig.DEBUG) {
                    val loggingInterceptor = HttpLoggingInterceptor()
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(loggingInterceptor)
                }
            }
            .build()

        private val instance : ApiService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(ApiService::class.java)
        }

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context) = synchronized(ViewModelFactory::class.java) {
            INSTANCE ?: ViewModelFactory(
                GithubRepository(
                    instance
                ),
                context
            ).also { INSTANCE = it }
        }
    }
}