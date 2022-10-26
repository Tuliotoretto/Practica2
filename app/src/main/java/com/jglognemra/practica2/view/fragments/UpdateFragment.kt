package com.jglognemra.practica2.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jglognemra.practica2.R
import com.jglognemra.practica2.databinding.FragmentUpdateBinding
import com.jglognemra.practica2.model.Song
import com.jglognemra.practica2.viewmodel.SongViewModel

class UpdateFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mSongViewModel: SongViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        mSongViewModel = ViewModelProvider(this)[SongViewModel::class.java]

        binding.etTitle.setText(args.currentSong.title)
        binding.etBand.setText(args.currentSong.band)

        // Spinner Config
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.genres_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerGenres.adapter = adapter
            binding.spinnerGenres.onItemSelectedListener = this
        }

        requireContext().resources.getStringArray(R.array.genres_array)
            .indexOf(args.currentSong.genre)
            .also { pos ->
            binding.spinnerGenres.setSelection(pos)
        }

        // button
        binding.btnUpdate.setOnClickListener {
            updateItem()
        }

        // menu
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        binding.ivImage.setImageResource(getIconAt(p2))
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    private fun updateItem() {
        val title = binding.etTitle.text.toString()
        val band = binding.etBand.text.toString()
        val genre = binding.spinnerGenres.selectedItem.toString()

        if (inputCheck(title, band, genre)) {
            val updatedSong = Song(args.currentSong.id, title, band, genre)
            mSongViewModel.updateSong(updatedSong)

            Toast.makeText(requireContext(),"Song updated!",Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(),"Fill all fields.",Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                deleteSong()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteSong() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setPositiveButton("Yes") {_,_ ->
            mSongViewModel.deleteSong(args.currentSong)
            Toast.makeText(requireContext(),"Song removed",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        alertDialog.setNegativeButton("No") {_, _ ->

        }
        alertDialog.setTitle("Delete ${args.currentSong.title}?")
        alertDialog.setMessage("Are you sure you want to delete ${args.currentSong.title}?")
        alertDialog.create().show()
    }

    private fun inputCheck(title: String, band: String, genre: String): Boolean {
        return (title.isNotEmpty() && band.isNotEmpty() && genre.isNotEmpty())
    }

    private fun getIconAt(pos: Int): Int {
        return when (pos) {
            0 -> R.drawable.ic_pop
            1 -> R.drawable.ic_rock
            2-> R.drawable.ic_hiphop
            3 -> R.drawable.ic_jazz
            4 -> R.drawable.ic_reggaeton
            else -> R.drawable.ic_add
        }
    }
}