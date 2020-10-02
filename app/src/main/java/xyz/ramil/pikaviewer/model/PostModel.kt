package xyz.ramil.pikaviewer.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
data class PostModel(
    @PrimaryKey
    var id: Long?,
    var title: String?,
    var body: String?,
    @TypeConverters
    val images: List<String?>?,
    var save: Boolean? = false
)



