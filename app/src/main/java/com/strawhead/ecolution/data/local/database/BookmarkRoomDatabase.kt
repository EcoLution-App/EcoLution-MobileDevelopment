package com.strawhead.ecolution.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Bookmark::class], version = 1)
abstract class BookmarkRoomDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
    companion object {
        @Volatile
        var INSTANCE: BookmarkRoomDatabase? = null
        fun getDatabase(context: Context): BookmarkRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    BookmarkRoomDatabase::class.java,
                    "bookmark_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}