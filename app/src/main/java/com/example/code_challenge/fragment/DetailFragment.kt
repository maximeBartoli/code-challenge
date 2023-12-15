package com.example.code_challenge.fragment

import android.location.GnssAntennaInfo.Listener
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.paging.map
import com.example.code_challenge.R
import com.example.code_challenge.adapter.ItemAdapter
import com.example.code_challenge.data.AppDatabase
import com.example.code_challenge.data.ArticleEntity
import com.example.code_challenge.model.Article
import com.example.code_challenge.model.onItemClickListener
import com.example.code_challenge.viewModel.ListViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect


class DetailFragment : Fragment() {

    private lateinit var mViewModel: ListViewModel
    private lateinit var mWebView: WebView
    private var id: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = ViewModelProvider(this)[ListViewModel::class.java]
        val activity = requireActivity() as AppCompatActivity
        val title = view.findViewById<TextView>(R.id.detailTitle)
        mWebView = view.findViewById(R.id.web)
        mWebView.settings.javaScriptEnabled = true

        val toolbar: Toolbar = view.findViewById(R.id.my_toolbar)
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        id = arguments?.getInt("idArticle") ?: 0

        val article = AppDatabase.getDatabase().articleDao().getArticleById(id).observe(viewLifecycleOwner) { article: ArticleEntity? ->
            article?.let {
                title.text= article.title
                val htmlImgStyle = "<style>img{display: inline;height: auto;max-width: 100%;}iframe{display: inline;height: auto;max-width: 100%;}</style>"
                        mWebView.loadData(
                            htmlImgStyle + article.content,
                            "text/html; charset=utf-8",
                            "UTF-8"
                        )
            }
        }
    }
}