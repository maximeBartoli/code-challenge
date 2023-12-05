package com.example.code_challenge

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.code_challenge.data.AppDatabase
import com.example.code_challenge.data.ArticleEntity
import com.example.code_challenge.data.DatabaseInitializer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = AppDatabase

        db.getInstance(this)

        GlobalScope.launch {
            val result = db.getDatabase().articleDao().insert(ArticleEntity(
                1232,
                "test",
                "ceci est un test",
                "ceci est une image",
                "Mon contenu"))
            Log.d("db","$result")
        }
    }
}