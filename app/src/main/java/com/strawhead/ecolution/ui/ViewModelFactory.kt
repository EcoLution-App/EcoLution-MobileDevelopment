package com.strawhead.ecolution.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.strawhead.ecolution.ui.screen.addhome.AddHomeViewModel

class ViewModelFactory() :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddHomeViewModel::class.java)) {
            return AddHomeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}