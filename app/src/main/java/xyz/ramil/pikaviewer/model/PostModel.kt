package xyz.ramil.pikaviewer.model

data class PostModel(
    var id: Long?,
    var title: String?,
    var body: String?,
    val images: List<String?>
)

