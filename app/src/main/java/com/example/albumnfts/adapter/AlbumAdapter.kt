package com.example.albumnfts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.albumnfts.databinding.AlbumFilaBinding
import com.example.albumnfts.model.Album
import com.example.albumnfts.ui.album.AlbumFragmentDirections

class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    //una lista para gestionar la información de los albumes
    private var lista = emptyList<Album>()

    inner class AlbumViewHolder(private val itemBinding: AlbumFilaBinding)
        : RecyclerView.ViewHolder (itemBinding.root){
        fun dibuja(album: Album) {
            itemBinding.tvNombre.text = album.nombre
            itemBinding.tvPrecio.text = album.precio
            itemBinding.tvCantidad.text = album.cantidad
            itemBinding.tvColor.text = album.color
            itemBinding.vistaFila.setOnClickListener {
                val accion = AlbumFragmentDirections
                    .actionNavAlbumToUpdateAlbumFragment(album)
                itemView.findNavController().navigate(accion)
            }
        }
    }

    //Acá se va a crear una "cajita" del reciclador...  una fila... sólo la estructura
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemBinding =
            AlbumFilaBinding.inflate(LayoutInflater.from(parent.context),
                parent,false)
        return AlbumViewHolder(itemBinding)
    }

    //Acá se va a solicitar "dibujar" una cajita, según el elemento de la lista...
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = lista[position]
        holder.dibuja(album)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    fun setData(albumes: List<Album>) {
        lista = albumes
        notifyDataSetChanged()
    }

}