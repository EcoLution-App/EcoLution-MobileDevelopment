package com.strawhead.ecolution.ui.screen.bookmark

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BookMarkViewModelFactory (private val context: Context) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: BookMarkViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): BookMarkViewModelFactory {
            if (INSTANCE == null) {
                synchronized(BookMarkViewModelFactory::class.java) {
                    INSTANCE = BookMarkViewModelFactory(application)
                }
            }
            return INSTANCE as BookMarkViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            return BookmarkViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}