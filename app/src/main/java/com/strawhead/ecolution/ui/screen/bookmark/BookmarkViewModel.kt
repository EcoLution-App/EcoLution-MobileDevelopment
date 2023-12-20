package com.strawhead.ecolution.ui.screen.bookmark

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strawhead.ecolution.data.local.database.Bookmark
import com.strawhead.ecolution.data.local.repository.BookmarkRepository
import com.strawhead.ecolution.data.remote.response.GetAllHouseResponseItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookmarkViewModel(context: Context): ViewModel()  {
    private val mBookmarkRepository: BookmarkRepository = BookmarkRepository(context)

    private val _state = MutableStateFlow<List<Bookmark>>(emptyList())
    val state = _state.asStateFlow()

    init {
        GlobalScope.launch{
            _state.value = mBookmarkRepository.getAllBookmarks()
        }
    }
    fun reload() {
        GlobalScope.launch{
            _state.value = mBookmarkRepository.getAllBookmarks()
        }
    }
    fun getAllBookmarks(): List<Bookmark> = mBookmarkRepository.getAllBookmarks()
    fun insert(bookmark: Bookmark) {
        mBookmarkRepository.insert(bookmark)
    }

    fun delete(bookmark: Bookmark) {
        mBookmarkRepository.delete(bookmark)
    }

    fun getBookmarkImageUrl(image: String): Bookmark = mBookmarkRepository.getBookmarkByImage(image)
}