package com.example.code_challenge.viewModel
import android.app.Application
import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.code_challenge.data.AppDatabase
import com.example.code_challenge.data.ArticleDao
import com.example.code_challenge.data.ArticleEntity
import com.example.code_challenge.data.ArticlePagingSource
import com.example.code_challenge.data.ArticleRemoteMediator
import com.example.code_challenge.model.Article
import com.example.code_challenge.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
class ListViewModel(application: Application) : AndroidViewModel(application) {
    private val appDatabase: AppDatabase = AppDatabase.getInstance(application)

    @OptIn(ExperimentalPagingApi::class)
    private val pager = Pager(
        config = PagingConfig(
            pageSize = ArticleRemoteMediator.PAGE_SIZE,
            prefetchDistance = 0,
            initialLoadSize = 1
        ),
        remoteMediator = ArticleRemoteMediator(appDatabase, Api.retrofitService)
    ) {
        appDatabase.articleDao().getAllArticles()
    }

    val paginatedArticleList: LiveData<PagingData<ArticleEntity>> =
        pager.flow
            .cachedIn(viewModelScope)
            .asLiveData()
}


