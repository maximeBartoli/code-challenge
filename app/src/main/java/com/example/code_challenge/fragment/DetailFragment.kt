package com.example.code_challenge.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.code_challenge.R
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

        val activity = requireActivity() as AppCompatActivity
        val title = view.findViewById<TextView>(R.id.detailTitle)
        val position = arguments?.getInt("article")
        val toolbar: Toolbar = view.findViewById(R.id.my_toolbar)

        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        mWebView = view.findViewById(R.id.web)
        mWebView.settings.javaScriptEnabled = true

        mViewModel = ViewModelProvider(this)[ListViewModel::class.java]
        mViewModel.fetchDataFromApi()

        mViewModel.itemList.observe(viewLifecycleOwner, Observer { itemList ->
            if (itemList?.items?.isNotEmpty() == true && position != null) {
                val article = itemList.items[position]
                title.text = article.title

                val htmlImgStyle = "<style>img{display: inline;height: auto;max-width: 100%;}iframe{display: inline;height: auto;max-width: 100%;}</style>"
                mWebView.loadData(htmlImgStyle + article.content, "text/html; charset=utf-8", "UTF-8")
            }


        })

    }

}