package com.example.code_challenge.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import com.example.code_challenge.model.Article


@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "date") val date: String,

    )

fun ArticleEntity.toArticle(): Article{
    return Article(
        id = id,
        title = title,
        description = description,
        image = image,
        content = content,
        date=date
    )
}