package com.example.code_challenge.viewModel
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.code_challenge.data.AppDatabase
import com.example.code_challenge.data.ArticleEntity
import com.example.code_challenge.data.ArticleRemoteMediator
import com.example.code_challenge.network.Api

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private val appDatabase: AppDatabase = AppDatabase.getInstance(application)
    private val applicationContext: Context = application.applicationContext


    @OptIn(ExperimentalPagingApi::class)
    private val pager = Pager(
        config = PagingConfig(
            pageSize = 1,
//            prefetchDistance = 5,
//            initialLoadSize = 1,
        ),
        remoteMediator = ArticleRemoteMediator(applicationContext,appDatabase, Api.retrofitService)
    ) {
        appDatabase.articleDao().getAllArticles()
    }

    val paginatedArticleList: LiveData<PagingData<ArticleEntity>> =
        pager.flow
            .cachedIn(viewModelScope)
            .asLiveData()
}


