package ms.saghafi.baloot.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ms.saghafi.baloot.utils.Converters

@Entity
@TypeConverters(Converters::class)
data class Article (
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    @PrimaryKey (autoGenerate = false)
    val url: String,
    val urlToImage: String? = null,
    val publishedAt: String,
    val content: String
)