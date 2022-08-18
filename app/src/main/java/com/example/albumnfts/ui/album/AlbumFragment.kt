package com.example.albumnfts.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.albumnfts.R
import com.example.albumnfts.adapter.AlbumAdapter
import com.example.albumnfts.databinding.FragmentAlbumBinding
import com.example.albumnfts.viewmodel.AlbumViewModel


class AlbumFragment : Fragment() {
    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var albumViewModel: AlbumViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        albumViewModel =
            ViewModelProvider(this).get(AlbumViewModel::class.java)
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        binding.addAlbum.setOnClickListener {
            findNavController().navigate(R.id.action_nav_album_to_addAlbumFragment)
        }

        //Activar el reciclador...
        val AlbumAdapter= AlbumAdapter()
        val reciclador = binding.reciclador
        reciclador.adapter = AlbumAdapter
        reciclador.layoutManager = LinearLayoutManager(requireContext())
        albumViewModel.getAllData.observe(viewLifecycleOwner) {
            AlbumAdapter.setData(it)
        }


        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}