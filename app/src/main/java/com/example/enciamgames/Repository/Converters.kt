package com.example.enciamgames.Repository

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.enciamgames.JeuVideo
import com.squareup.moshi.Moshi

@ProvidedTypeConverter
class Converters(moshi: Moshi) {
    private val jeuxVideoAdapter = moshi.adapter(List::class.java)

    @TypeConverter
    fun StringToJeuxVideoList(value: String?): List<JeuVideo>? {
        return jeuxVideoAdapter.fromJson(value.orEmpty()) as List<JeuVideo>?
    }
    @TypeConverter
    fun JeuxVideoListToString(jeuxvideo: List<JeuVideo>?): String? {
        return jeuxVideoAdapter.toJson(jeuxvideo)
    }

}