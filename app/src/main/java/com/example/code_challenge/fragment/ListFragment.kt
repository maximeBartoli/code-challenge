package com.example.code_challenge.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.code_challenge.R
import com.example.code_challenge.adapter.ItemAdapter
import com.example.code_challenge.model.onItemClickListener
import com.example.code_challenge.viewModel.ListViewModel


class ListFragment : Fragment() {

    private lateinit var mViewModel: ListViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mManager: RecyclerView.LayoutManager
    private lateinit var mItemAdapter: ItemAdapter
    private var navController: NavController? = null

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
        navController = view.findNavController()


        mRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            mItemAdapter = ItemAdapter(object : onItemClickListener {
                override fun onItemclick(position: Int) {
                    var id = mItemAdapter.snapshot().items[position].id
                    val bundle = bundleOf("idArticle" to id)
                    navController?.navigate(R.id.action_listFragment_to_detailFragment, bundle)
                }
            })

            layoutManager = mManager
            adapter = mItemAdapter
        }

//        mViewModel.fetchData()

        mViewModel.paginatedArticleList.observe(viewLifecycleOwner, Observer { pagingData ->
            mItemAdapter.submitData(lifecycle, pagingData)
        })
    }
}