package com.example.code_challenge.fragment

import android.os.Bundle
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

    companion object {
        fun newInstance() = ListFragment()
    }

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

        mRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply{
            mItemAdapter = ItemAdapter()
            layoutManager = mManager
            adapter = mItemAdapter


        }

        mViewModel.fetchDataFromApi()
        mViewModel.itemList.observe(viewLifecycleOwner, Observer { itemList ->
            val list = itemList.items
            mItemAdapter.setNotify(list)
        })


        mItemAdapter.setOnItemClickListener(object : onItemClickListener{
            override fun onItemclick(article: Int) {
//                Toast.makeText(requireContext(), "You click $article", Toast.LENGTH_SHORT).show()
                navController = view.findNavController()
                val bundle = bundleOf("article" to article)

                navController.let {
                    it?.navigate(R.id.action_listFragment_to_detailFragment,bundle)
                }

            }
        })
    }

}