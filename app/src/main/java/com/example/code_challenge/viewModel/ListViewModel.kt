package com.example.code_challenge.viewModel
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.code_challenge.data.ArticlePagingSource
import com.example.code_challenge.model.Article
import com.example.code_challenge.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListViewModel() : ViewModel() {

    private val _paginatedArticleList = MutableLiveData<PagingData<Article>>()
    val paginatedArticleList: LiveData<PagingData<Article>> get() = _paginatedArticleList

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Pager(
                    config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
                    pagingSourceFactory = { ArticlePagingSource(Api) }
                ).flow.cachedIn(viewModelScope).collectLatest {
                    _paginatedArticleList.postValue(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}


