package com.example.albumnfts.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.albumnfts.data.AlbumDao
import com.example.albumnfts.model.Album
import com.example.albumnfts.repository.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumViewModel(application: Application) : AndroidViewModel(application) {
    //Atributo para obtener la lista de albumes en un ArrayList especial
    val getAllData: MutableLiveData<List<Album>>
    //Atributo para acceder al repositorio de Album
    private val repository: AlbumRepository = AlbumRepository(AlbumDao())

    //Bloque de inicialización de los atributos
    init {  getAllData = repository.getAllData  }

    fun saveAlbum(album: Album) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveAlbum(album)
        }
    }

    fun deleteAlbum(album: Album) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAlbum(album)
        }
    }
}