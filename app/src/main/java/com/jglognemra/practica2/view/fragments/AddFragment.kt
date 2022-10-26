package com.jglognemra.practica2.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jglognemra.practica2.R
import com.jglognemra.practica2.model.Song
import com.jglognemra.practica2.databinding.FragmentAddBinding
import com.jglognemra.practica2.viewmodel.SongViewModel

class AddFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var mSongViewModel: SongViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        mSongViewModel = ViewModelProvider(this)[SongViewModel::class.java]

        binding.btnAdd.setOnClickListener {
            insertDataToDatabase()
        }

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

        return binding.root
    }

    private fun insertDataToDatabase() {
        val title = binding.etTitle.text.toString()
        val band = binding.etBand.text.toString()
        val genre = binding.spinnerGenres.selectedItem.toString()

        if (inputCheck(title, band, genre)) {
            val song = Song(0, title, band, genre)
            mSongViewModel.addSong(song)
            Toast.makeText(requireContext(), "Song added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        binding.ivImage.setImageResource(getIconAt(p2))
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {}

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