package com.example.albumnfts.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.albumnfts.model.Album
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class AlbumDao {

    private val coleccion1 = "albumApp"
    private val usuario= Firebase.auth.currentUser?.email.toString()
    private val coleccion2 = "NFTs"
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    fun getAllData() : MutableLiveData<List<Album>> {
        val listaAlbumes = MutableLiveData<List<Album>>()
        firestore.collection(coleccion1).document(usuario).collection(coleccion2)
            .addSnapshotListener{ instantanea, e ->
                if (e != null) {  //Se valida si se generó algún error en la captura de los documentos
                    return@addSnapshotListener
                }
                if (instantanea != null) {  //Si hay información recuperada...
                    //Recorro la instantanea (documentos) para crear la lista de albumes
                    val lista = ArrayList<Album>()
                    instantanea.documents.forEach {
                        val album = it.toObject(Album::class.java)
                        if (album!=null) { lista.add(album) }
                    }
                    listaAlbumes.value=lista
                }
            }
        return listaAlbumes
    }

    fun saveAlbum(album: Album) {
        val documento: DocumentReference
        if (album.id.isEmpty()) {  //Si id no tiene valor entonces es un documento nuevo
            documento = firestore.collection(coleccion1).document(usuario).collection(coleccion2).document()
            album.id = documento.id
        } else {  //si el id tiene valor... entonces el documento existe... y recupero la info de él
            documento = firestore.collection(coleccion1).document(usuario)
                .collection(coleccion2).document(album.id)
        }
        documento.set(album)
            .addOnSuccessListener { Log.d("saveAlbum","Se creó o modificó un album") }
            .addOnCanceledListener { Log.e("saveAlbum","NO se creó o modificó un album") }
    }

    fun deleteAlbum(album: Album) {
        if (album.id.isNotEmpty()) {  //Si el id tiene valor... entonces podemos eliminar el album... porque existe...
            firestore.collection(coleccion1).document(usuario)
                .collection(coleccion2).document(album.id).delete()
                .addOnSuccessListener { Log.d("deleteAlbum","Se elimintó un album") }
                .addOnCanceledListener { Log.e("deleteAlbum","NO se eliminó un album") }
        }
    }
}