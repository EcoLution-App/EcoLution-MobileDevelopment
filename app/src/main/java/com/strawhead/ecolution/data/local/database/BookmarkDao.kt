package com.strawhead.ecolution.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(bookmark: Bookmark)

    @Update
    fun update(bookmark: Bookmark)

    @Delete
    fun delete(bookmark: Bookmark)

    @Query("SELECT * from bookmark ORDER BY id ASC")
    fun getAllBookmark(): List<Bookmark>

    @Query("SELECT * FROM bookmark WHERE image = :image")
    fun getBookmarkByImage(image: String): Bookmark

}