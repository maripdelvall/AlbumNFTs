package com.example.albumnfts.repository

import androidx.lifecycle.MutableLiveData
import com.example.albumnfts.data.AlbumDao
import com.example.albumnfts.model.Album

class AlbumRepository(private val AlbumDao: AlbumDao) {

    fun saveAlbum(album: Album) {
        AlbumDao.saveAlbum(album)
    }

    fun deleteAlbum(album: Album) {
        AlbumDao.deleteAlbum(album)
    }

    val getAllData : MutableLiveData<List<Album>> = AlbumDao.getAllData()
}