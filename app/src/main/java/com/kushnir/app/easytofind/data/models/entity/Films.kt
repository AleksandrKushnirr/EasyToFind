package com.kushnir.app.easytofind.data.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kushnir.app.easytofind.domain.enums.RatingColor
import com.kushnir.app.easytofind.domain.models.ImageModel

@Entity
data class Films(
        @PrimaryKey(autoGenerate = false) val id : Int,
        val name : String,
        val year : String,
        val filmLength : String?,
        val countries : List<String>,
        val genres : List<String>,
        val rating : String,
        val poster : ImageModel,
        val ratingColor: RatingColor? = null
)
