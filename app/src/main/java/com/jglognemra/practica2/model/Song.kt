package com.jglognemra.practica2.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "songs_table")
data class Song(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val band: String,
    val genre: String,
): Parcelable
