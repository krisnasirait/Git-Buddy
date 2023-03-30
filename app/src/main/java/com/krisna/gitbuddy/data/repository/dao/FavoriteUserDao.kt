package com.krisna.gitbuddy.data.repository.dao

import androidx.room.*
import com.krisna.gitbuddy.data.entity.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM User")
    fun getAllUser(): List<FavoriteUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(favoriteUser: FavoriteUser)

    @Delete
    fun deleteUser(favoriteUser: FavoriteUser)
}