package com.example.code_challenge.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.code_challenge.model.Article
import com.example.code_challenge.model.toArticleEntity
import com.example.code_challenge.network.ApiService
import retrofit2.awaitResponse
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val db : AppDatabase,
    private  val Api : ApiService
) : RemoteMediator<Int,ArticleEntity>(){

    companion object {
        const val PAGE_SIZE = 5
        var page = 1
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {

        return try {
            val loadKey = when(loadType){
                LoadType.REFRESH -> {
                    1
                }
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }else{
                        ++page
                    }
                }
            }
            Log.d("art", "Rm :$loadType, load key: $loadKey")

            val response = Api.getAllData(
                page = loadKey,
                perPage = PAGE_SIZE
            ).awaitResponse()

            if (response.isSuccessful) {

                Log.d("art","Rm : response API succes")

                val articles: List<Article> = response.body()?.items ?: emptyList()
                try {
                    db.withTransaction {
                        if(loadType == LoadType.REFRESH){
                            db.articleDao().deleteAll()
                        }
                        val articleEntities = articles.map { it.toArticleEntity() }
                        db.articleDao().upsertAll(articleEntities)
                    }
                }
                catch (e:Exception){
                    Log.e("art", "Erreur lors de la transaction de base de données", e)
                }

                MediatorResult.Success(
                    endOfPaginationReached = articles.isEmpty()
                )
            }else{
                MediatorResult.Error(error("Api ne répond pas"))
            }


        }catch (e:Exception){
            Log.e("art", "Erreur lors du chargement des données", e)
            MediatorResult.Error(e)
        }
    }

}