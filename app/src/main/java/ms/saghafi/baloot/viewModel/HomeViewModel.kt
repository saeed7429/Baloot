package ms.saghafi.baloot.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ms.saghafi.baloot.utils.ApiStatus
import kotlinx.coroutines.launch
import ms.saghafi.baloot.model.Article
import ms.saghafi.baloot.room.ArticlesRepository
import ms.saghafi.baloot.room.LocalDatabase
import retrofit2.HttpException
import java.lang.Exception

class HomeViewModel(application: Application) : AndroidViewModel(application){

    private val apiKey = "4980b4cd36564041a7c495cbf42440ad"

    private val _message = MutableLiveData<String>()
    val message : LiveData<String>
        get() = _message

    private val _status = MutableLiveData<ApiStatus>()
    val status : LiveData<ApiStatus>
        get() = _status

    private val articlesRepository = ArticlesRepository(LocalDatabase.getDatabase(application))
    val allArticles = articlesRepository.allArticles

    // Selected Article For Pass It From Home Fragment To Article Fragment
    lateinit var selectedArticle: Article

    // Load Requested Page Articles And Save it To Local Database:
    fun getArticles(page: Long){
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                articlesRepository.refreshArticle("android",apiKey,20,page)
                _status.value = ApiStatus.DONE
            } catch (exception: HttpException) {

                when (exception.code()) {
                    426 -> _message.value = "خبر دیگری جهت نمایش موجود نیست"
                    429 -> _message.value = "محدودیت تعداد درخواست های روزانه. لطفا بعدا تلاش نمایید"
                    else -> _message.value = "بروز خطای ناشناخته از سمت سرور"
                }
                _status.value = ApiStatus.ERROR
            } catch (exception: Exception) {
                _message.value = exception.localizedMessage
                _status.value = ApiStatus.ERROR
            }
            _message.value = ""
        }
    }

    // Delete Articles From Local Database (Delete Cache) :
    fun deleteArticles(){
        viewModelScope.launch {
            articlesRepository.deleteAll()
        }
    }
}