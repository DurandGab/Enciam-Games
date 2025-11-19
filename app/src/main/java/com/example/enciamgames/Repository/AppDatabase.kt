package com.example.enciamgames.Repository

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.enciamgames.JeuVideo
import com.example.enciamgames.JeuVideoFavori

@Database(entities = [JeuVideoFavori::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jeuxVideoFavoriDao(): JeuxVideosFavoriDao
}