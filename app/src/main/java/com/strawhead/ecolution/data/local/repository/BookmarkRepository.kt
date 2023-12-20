package com.strawhead.ecolution.data.local.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.strawhead.ecolution.data.local.database.Bookmark
import com.strawhead.ecolution.data.local.database.BookmarkDao
import com.strawhead.ecolution.data.local.database.BookmarkRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class BookmarkRepository(context: Context) {
    private val mBookmarkDao: BookmarkDao

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = BookmarkRoomDatabase.getDatabase(context)
        mBookmarkDao = db.bookmarkDao()
    }

    fun getAllBookmarks(): List<Bookmark> = mBookmarkDao.getAllBookmark()

    fun getBookmarkByImage(image: String): Bookmark = mBookmarkDao.getBookmarkByImage(image)

    fun insert(bookmark: Bookmark) {
        executorService.execute { mBookmarkDao.insert(bookmark) }
    }

    fun delete(bookmark: Bookmark) {
        executorService.execute { mBookmarkDao.delete(bookmark) }
    }

}