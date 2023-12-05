package com.example.code_challenge.data
import android.content.Context
import android.util.Log
import androidx.room.Room

object DatabaseInitializer {

    private var database: AppDatabase? = null

    fun initialize(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "articleDatabase")
            .fallbackToDestructiveMigration()
            .build()
        Log.d("test","db initialiezd")
    }

    fun getDatabase(): AppDatabase {
        return database ?: throw IllegalStateException("Database not initialized")
    }
}