package com.example.code_challenge.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<ArticleEntity>)

    @Insert
    suspend fun insert(articleEntity: ArticleEntity)

    @Query("SELECT * FROM articles ORDER BY id ASC")
    fun getAllArticles(): PagingSource<Int, ArticleEntity>

    @Query("SELECT * FROM articles WHERE id == :id")
    fun getArticleById(id:Int): PagingSource<Int, ArticleEntity>
}
