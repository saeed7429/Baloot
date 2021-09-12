package ms.saghafi.baloot.model

import androidx.room.Entity

data class BaseResponseModel (
    val status: String,
    val totalResults: Long,
    val articles: List<Article>
)
