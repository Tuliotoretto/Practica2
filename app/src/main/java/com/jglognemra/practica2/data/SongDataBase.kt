package com.jglognemra.practica2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jglognemra.practica2.model.Song

@Database(entities = [Song::class], version = 1, exportSchema = false)
abstract class SongDataBase: RoomDatabase() {

    abstract fun songDao(): SongDao

    companion object {

        @Volatile
        private var INSTANCE: SongDataBase? = null

        fun getDatabase(context: Context): SongDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SongDataBase::class.java,
                    "song_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance

                return instance
            }
        }
    }
}