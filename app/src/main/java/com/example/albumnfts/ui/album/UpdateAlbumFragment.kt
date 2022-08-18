package com.example.albumnfts.ui.album

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.albumnfts.R
import com.example.albumnfts.databinding.FragmentUpdateAlbumBinding
import com.example.albumnfts.model.Album
import com.example.albumnfts.viewmodel.AlbumViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UpdateAlbumFragment : Fragment() {

    //Se deciben los parametros pasados por argumento
    private val args by navArgs<UpdateAlbumFragmentArgs>()

    private var _binding: FragmentUpdateAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var albumViewModel: AlbumViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        albumViewModel =
            ViewModelProvider(this)[AlbumViewModel::class.java]
        _binding = FragmentUpdateAlbumBinding.inflate(inflater, container, false)

        //Coloco la info del album en los campos del fragmento... para modificar...
        binding.etNombre.setText(args.album.nombre)
        binding.etPrecio.setText(args.album.precio)
        binding.etCantidad.setText(args.album.cantidad)
        binding.etColor.setText(args.album.color)


        binding.imagePreview.setImageURI(Uri.parse(args.album.URL))

        binding.btUpdateAlbum.setOnClickListener { updateAlbum() }



        //Se indica que esta pantalla tiene un menu personalizado...
        setHasOptionsMenu(true)

        return binding.root
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Consulto si se dio click en el ícono de borrar
        if (item.itemId==R.id.menu_delete) {
            deleteAlbum()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAlbum() {
        val pantalla=AlertDialog.Builder(requireContext())

        pantalla.setTitle(R.string.delete)
        pantalla.setMessage(getString(R.string.seguroBorrar)+" ${args.album.nombre}?")

        pantalla.setPositiveButton(getString(R.string.si)) { _,_ ->
            albumViewModel.deleteAlbum(args.album)
            findNavController().navigate(R.id.action_updateAlbumFragment_to_nav_album)
        }

        pantalla.setNegativeButton(getString(R.string.no)) { _,_ -> }
        pantalla.create().show()
    }


    //Por acá estamos por el momento...
    private fun updateAlbum() {
        val nombre=binding.etNombre.text.toString()
        val precio=binding.etPrecio.text.toString()
        val cantidad=binding.etCantidad.text.toString()
        val color=binding.etColor.text.toString()

        //val imageUri = binding.image_preview

        if (nombre.isNotEmpty()) { //Si puedo crear un album
            val album= Album(args.album.id,nombre,precio, cantidad, color,null)

            albumViewModel.saveAlbum(album)

            Toast.makeText(requireContext(),getString(R.string.msg_album_update),Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateAlbumFragment_to_nav_album)
        } else {  //Mensaje de error...
            Toast.makeText(requireContext(),getString(R.string.msg_data),Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}