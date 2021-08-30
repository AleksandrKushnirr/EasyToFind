package com.kushnir.app.easytofind.data.room.type_converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.kushnir.app.easytofind.domain.models.ImageModel

object RoomConverters {

    @TypeConverter
    fun toListOfStrings(flatStringList: String): List<String> {
        return flatStringList.split(",")
    }
    @TypeConverter
    fun fromListOfStrings(listOfString: List<String>): String {
        return listOfString.joinToString(",")
    }

    @TypeConverter
    fun imagePosterModelToJson(image: ImageModel): String {
        return Gson().toJson(image)
    }

    @TypeConverter
    fun jsonToImagePosterModel(string: String): ImageModel {
        return Gson().fromJson(string, ImageModel::class.java)
    }
}