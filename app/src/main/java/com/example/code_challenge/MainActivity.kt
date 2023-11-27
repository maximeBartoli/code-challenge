package com.example.code_challenge

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.code_challenge.adapter.ItemAdapter
import com.example.code_challenge.fragment.DetailFragment
import com.example.code_challenge.fragment.ListFragment
import com.example.code_challenge.viewModel.ListViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var mViewModel: ListViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mManager: RecyclerView.LayoutManager
    private lateinit var mItemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Call the List Fragment & link to the view
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.home, ListFragment.newInstance())
                .commit()
        }
    }
}