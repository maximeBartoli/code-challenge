package com.example.code_challenge.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<ArticleEntity>)

    @Upsert
    suspend fun upsertAll(articles: List<ArticleEntity>)

    @Insert
    suspend fun insert(articleEntity: ArticleEntity)

    @Query("SELECT * FROM articles ORDER BY date DESC")
    fun getAllArticles(): PagingSource<Int, ArticleEntity>

    @Query("SELECT * FROM articles WHERE id == :id")
    fun getArticleById(id:Int): LiveData<ArticleEntity?>

    @Query("DELETE FROM articles")
    suspend fun deleteAll()

}
