package com.example.enciamgames.Repository

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.enciamgames.Models.JeuVideo
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

@ProvidedTypeConverter
class Converters(moshi: Moshi) {
    private val listType = Types.newParameterizedType(
        List::class.java,
        JeuVideo::class.java
    )

    private val jeuxVideoAdapter = moshi.adapter(JeuVideo::class.java)

    @TypeConverter
    fun fromString(value: String?): JeuVideo? {
       return jeuxVideoAdapter.fromJson(value)
    }

    @TypeConverter
    fun toString(jeu: JeuVideo): String {
        return jeuxVideoAdapter.toJson(jeu)
    }
}