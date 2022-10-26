package com.jglognemra.practica2.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jglognemra.practica2.model.Song

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSong(song: Song)

    @Update
    suspend fun updateSong(song: Song)

    @Delete
    suspend fun deleteSong(song: Song)

    @Query("DELETE FROM songs_table")
    suspend fun deleteAllSongs()

    @Query("SELECT * FROM songs_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Song>>
}