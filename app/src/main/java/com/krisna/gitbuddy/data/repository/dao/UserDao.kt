package com.krisna.gitbuddy.data.repository.dao

import androidx.room.*
import com.krisna.gitbuddy.data.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAllUser(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)
}