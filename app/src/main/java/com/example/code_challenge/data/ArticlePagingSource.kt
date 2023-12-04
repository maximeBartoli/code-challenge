package com.example.code_challenge.data
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.code_challenge.model.Article
import com.example.code_challenge.model.Item
import com.example.code_challenge.network.Api
import retrofit2.await
import retrofit2.awaitResponse
import kotlin.math.max


private const val STARTING_KEY = 0
class ArticlePagingSource(apiService: Api) : PagingSource<Int, Article> (){
    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
    private var response = apiService.retrofitService

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        try {
            var page = params.key ?: 1

            val response = response.getAllData(page,5).awaitResponse()

            if (response.isSuccessful) {
                val articles: List<Article> = response.body()?.items ?: emptyList()

                var prevKey = if (page == 1) null else page - 1
                var nextKey = if (articles.isEmpty()) null else page + 1

                return LoadResult.Page(
                    data = articles,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {

                return LoadResult.Error(Exception("API call failed, code: ${response.code()}"))
            }
        } catch (e: Exception) {

            return LoadResult.Error(e)
        }
    }
}