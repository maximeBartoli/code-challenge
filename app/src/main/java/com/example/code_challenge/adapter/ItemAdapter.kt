package com.example.code_challenge.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.code_challenge.R
import com.bumptech.glide.Glide
import com.example.code_challenge.data.ArticleEntity
import com.example.code_challenge.model.Article
import com.example.code_challenge.model.onItemClickListener


class ItemAdapter(private val listener: onItemClickListener?) :
    PagingDataAdapter<ArticleEntity, ItemAdapter.ItemViewHolder>(ArticleDiffCallback()) {

    class ItemViewHolder(private val view: View, private val listener: onItemClickListener?) :
        RecyclerView.ViewHolder(view) {

        private val defaultImage = R.drawable.default1
        private val title: TextView = view.findViewById(R.id.tvTitle)
        private val imageView: ImageView = view.findViewById(R.id.imageView)
        private val description: TextView = view.findViewById(R.id.tvDescription)

        init {
            view.setOnClickListener {
                listener?.onItemclick(bindingAdapterPosition)
            }
        }

        fun bind(article: ArticleEntity) {
            val image = if (article.image != "") article.image else defaultImage
            title.text = article.title
            description.text = article.description
            Glide.with(view.context).load(image).centerCrop().into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        if (listener !== null){
            return ItemViewHolder(adapterLayout, listener)
        }
        return ItemViewHolder(adapterLayout,null)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class ArticleDiffCallback : DiffUtil.ItemCallback<ArticleEntity>() {

        override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean {
            return oldItem == newItem
        }
    }
}