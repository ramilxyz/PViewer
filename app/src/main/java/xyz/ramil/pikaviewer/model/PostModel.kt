package xyz.ramil.pikaviewer.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import xyz.ramil.pikaviewer.database.Converters

@Entity
data class PostModel(
    @NonNull
    @PrimaryKey
    var id: Long?,
    var title: String?,
    var body: String?,
    @TypeConverters
    val images: List<String?>
)

