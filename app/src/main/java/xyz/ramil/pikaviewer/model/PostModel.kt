package xyz.ramil.pikaviewer.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class PostModel(
    @PrimaryKey
    var id: Long?,
    var title: String?,
    var body: String?
) {
    @Ignore
    val images: List<String?> = mutableListOf()
}



