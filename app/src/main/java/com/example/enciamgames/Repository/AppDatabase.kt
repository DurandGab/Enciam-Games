package com.example.enciamgames.Repository

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.enciamgames.JeuVideo
import com.example.enciamgames.JeuVideoFavori

@Database(entities = [JeuVideoFavori::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jeuxVideoFavoriDao(): JeuxVideosFavoriDao
}