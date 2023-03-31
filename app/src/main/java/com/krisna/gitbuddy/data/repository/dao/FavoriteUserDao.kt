package com.krisna.gitbuddy.data.repository.dao

import androidx.room.*
import com.krisna.gitbuddy.data.entity.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM FavoriteUser")
    suspend fun getAllUser(): List<FavoriteUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(favoriteUser: FavoriteUser)

    @Delete
    suspend fun deleteUser(favoriteUser: FavoriteUser)
}