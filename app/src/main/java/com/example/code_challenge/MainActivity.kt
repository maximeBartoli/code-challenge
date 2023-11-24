package com.example.code_challenge

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.code_challenge.adapter.ItemAdapter
import com.example.code_challenge.viewModel.ListViewModel


class MainActivity : ComponentActivity() {
    private lateinit var viewModel: ListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var mItemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(this)[ListViewModel::class.java]

        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply{
            mItemAdapter = ItemAdapter()
            layoutManager = manager
            adapter = mItemAdapter
        }

        viewModel.fetchDataFromApi()
        viewModel.itemList.observe(this, Observer { itemList ->
           mItemAdapter.setNotify(itemList.items)
        })

    }
}