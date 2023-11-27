package com.example.code_challenge.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.code_challenge.R
import com.example.code_challenge.adapter.ItemAdapter
import com.example.code_challenge.viewModel.ListViewModel

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var mViewModel: ListViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mManager: RecyclerView.LayoutManager
    private lateinit var mItemAdapter: ItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mManager = LinearLayoutManager(context)
        mViewModel = ViewModelProvider(this)[ListViewModel::class.java]

        mRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply{
            mItemAdapter = ItemAdapter()
            layoutManager = mManager
            adapter = mItemAdapter
        }

        mViewModel.fetchDataFromApi()
        mViewModel.itemList.observe(viewLifecycleOwner, Observer { itemList ->
            mItemAdapter.setNotify(itemList.items)
        })
    }
}