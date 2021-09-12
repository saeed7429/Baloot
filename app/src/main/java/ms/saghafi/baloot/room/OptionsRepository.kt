package ms.saghafi.baloot.room

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ms.saghafi.baloot.model.Article
import ms.saghafi.baloot.network.BaseApi

class ArticlesRepository(private val database: LocalDatabase) {

    val allArticles: LiveData<List<Article>> = database.articleDao.getAllArticles()

    suspend fun refreshArticle(q: String,apiKey: String, pageSize: Long, page:Long) {
        withContext(Dispatchers.IO) {

            val responseData = BaseApi.retrofitService.getArticles(q,apiKey,pageSize,page)
            database.articleDao.insertAll(responseData.articles)
            
        }
    }

    suspend fun insertArticle(article: Article){
        database.articleDao.insert(article)
    }

    suspend fun deleteAll(){
        database.articleDao.deleteAll()
    }

}