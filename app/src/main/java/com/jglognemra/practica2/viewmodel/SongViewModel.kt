package com.jglognemra.practica2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jglognemra.practica2.model.Song
import com.jglognemra.practica2.data.SongDataBase
import com.jglognemra.practica2.data.SongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SongViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Song>>
    private val repository: SongRepository

    init {
        val songDao = SongDataBase.getDatabase(application).songDao()
        repository = SongRepository(songDao)
        readAllData = repository.readAllData
    }

    fun addSong(song: Song) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSong(song)
        }
    }

    fun updateSong(song: Song) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSong(song)
        }
    }

    fun deleteSong(song: Song) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSong(song)
        }
    }

    fun deleteAllSongs() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllSongs()
        }
    }
}