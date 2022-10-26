package com.jglognemra.practica2.view.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jglognemra.practica2.R
import com.jglognemra.practica2.databinding.FragmentListBinding
import com.jglognemra.practica2.view.adapters.ListAdapter
import com.jglognemra.practica2.viewmodel.SongViewModel

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var mSongViewModel: SongViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)

        // RecyclerView
        val adapter = ListAdapter()
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        // songViewModel
        mSongViewModel = ViewModelProvider(this)[SongViewModel::class.java]
        mSongViewModel.readAllData.observe(viewLifecycleOwner, Observer { song ->
            adapter.setData(song)
        })

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        // menu
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        menu.findItem(R.id.menu_delete).icon?.setTint(Color.WHITE)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                deleteAllSongs()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllSongs() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setPositiveButton("Yes") {_,_ ->
            mSongViewModel.deleteAllSongs()
            Toast.makeText(requireContext(),"All Songs removed", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        alertDialog.setNegativeButton("No") {_, _ ->

        }
        alertDialog.setTitle("Delete All?")
        alertDialog.setMessage("Are you sure you want to delete everything?")
        alertDialog.create().show()
    }
}