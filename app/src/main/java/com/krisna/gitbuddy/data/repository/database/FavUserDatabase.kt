package com.krisna.gitbuddy.data.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.krisna.gitbuddy.data.entity.FavoriteUser
import com.krisna.gitbuddy.data.repository.dao.FavoriteUserDao

@Database(
    entities = [FavoriteUser::class],
    version = 1
)
abstract class FavUserDatabase : RoomDatabase() {
    abstract fun favUserDao(): FavoriteUserDao

    companion object {
        private var INSTANCE: FavUserDatabase? = null
        fun getInstance(context: Context): FavUserDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavUserDatabase::class.java,
                    "user-db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}