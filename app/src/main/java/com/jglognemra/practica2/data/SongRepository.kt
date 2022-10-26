package com.jglognemra.practica2.data

import androidx.lifecycle.LiveData
import com.jglognemra.practica2.model.Song

class SongRepository(private val songDao: SongDao) {

    val readAllData: LiveData<List<Song>> = songDao.readAllData()

    suspend fun addSong(song: Song) {
        songDao.addSong(song)
    }

    suspend fun updateSong(song: Song) {
        songDao.updateSong(song)
    }

    suspend fun deleteSong(song: Song) {
        songDao.deleteSong(song)
    }

    suspend fun deleteAllSongs() {
        songDao.deleteAllSongs()
    }
}