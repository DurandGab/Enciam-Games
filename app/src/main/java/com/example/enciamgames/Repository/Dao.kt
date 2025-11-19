package com.example.enciamgames.Repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.enciamgames.JeuVideo
import com.example.enciamgames.JeuVideoFavori

@Dao
interface JeuxVideosFavoriDao {
    @Query("SELECT * FROM JeuVideoFavori")
    fun getFavoris(): List<JeuVideoFavori>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun ajouterFavori(jeuVideo: JeuVideoFavori)

    @Query("DELETE FROM JeuVideoFavori WHERE id = :id")
    fun supprimerFavoriById(id: Int)

}