package ms.saghafi.baloot.utils

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import ms.saghafi.baloot.model.Source

class Converters {

    private val listAdapter =
            Moshi.Builder().build().adapter(Array<String>::class.java)
    @TypeConverter
    fun jsonToList(aString: String?): MutableList<String>? =
            aString?.let { listAdapter.fromJson(it)?.toMutableList() }
    @TypeConverter
    fun listToJson(aList: List<String>?): String? =
            aList?.let { listAdapter.toJson(it.toTypedArray()) }

    private val sourceAdapter =
        Moshi.Builder().build().adapter(Source::class.java)
    @TypeConverter
    fun jsonToSource(aString: String?): Source? =
        aString?.let { sourceAdapter.fromJson(it) }
    @TypeConverter
    fun sourceToJson(aSource: Source?): String? =
        aSource?.let { sourceAdapter.toJson(it) }

}