package com.example.code_challenge.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.code_challenge.R
import com.example.code_challenge.adapter.ItemAdapter
import com.example.code_challenge.viewModel.ListViewModel

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var mViewModel: ListViewModel
    private lateinit var mWebView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         mWebView = view.findViewById(R.id.web)


        mViewModel = ViewModelProvider(this)[ListViewModel::class.java]
        mViewModel.fetchDataFromApi()
        mViewModel.itemList.observe(viewLifecycleOwner, Observer { itemList ->
            mWebView.loadData(itemList.items[0].content, "text/html; charset=utf-8", "UTF-8");


        })

    }

}