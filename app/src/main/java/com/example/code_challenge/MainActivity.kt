package com.example.code_challenge

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.code_challenge.adapter.ItemAdapter
import com.example.code_challenge.model.Article
import com.example.code_challenge.model.Item
import com.example.code_challenge.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var itemAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = LinearLayoutManager(this)
        getAllData()
    }

    fun getAllData(){
        Log.d("getData", "ici dans la métode getAllData")

        Api.retrofitService.getAllData().enqueue(object: Callback<Item>{
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if(response.isSuccessful){
                    Log.d("getRes", "ici dans la responses${response.isSuccessful}")

                    recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply{
                        itemAdapter = ItemAdapter(context,response.body()?.items)
                        layoutManager = manager
                        adapter = itemAdapter
                    }
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}