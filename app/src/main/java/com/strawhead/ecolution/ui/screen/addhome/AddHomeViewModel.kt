package com.strawhead.ecolution.ui.screen.addhome

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import java.io.File

class AddHomeViewModel() : ViewModel() {
    private val _nama = MutableStateFlow("")
    val nama = _nama.asStateFlow()

    private val _harga = MutableStateFlow("")
    val harga = _harga.asStateFlow()

    private val _lokasi = MutableStateFlow("")
    val lokasi = _lokasi.asStateFlow()

    private val _image = MutableStateFlow<Uri?>(null)
    val image = _image.asStateFlow()

    private val _imageFile = MutableStateFlow<File?>(null)
    val imageFile = _imageFile.asStateFlow()

    private val _deskripsi = MutableStateFlow("")
    val deskripsi = _deskripsi.asStateFlow()
    fun changeNamaValue(cnt : String){
        _nama.value = cnt
    }

    fun changeHargaValue(cnt : String){
        _harga.value = cnt
    }

    fun changeLokasiValue(cnt: String){
        _lokasi.value = cnt
    }

    fun changeImageValue(cnt: Uri?){
        _image.value = cnt
    }

    fun changeImageFileValue(cnt: File?){
        _imageFile.value = cnt
    }

    fun changeDeskripsiValue(cnt: String){
        _deskripsi.value = cnt
    }
}