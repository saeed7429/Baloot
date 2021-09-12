package ms.saghafi.baloot.room

import androidx.lifecycle.LiveData
import androidx.room.*
import ms.saghafi.baloot.model.Article

@Dao
interface ArticleDao {

    @Query("select * from article")
    fun getAllArticles(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(foods: List<Article>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(food: Article)

    @Query("delete from article")
    suspend fun deleteAll()
}