package com.example.code_challenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.code_challenge.R
import com.bumptech.glide.Glide
import com.example.code_challenge.model.Article

class ItemAdapter(
    private var dataset: List<Article>? = arrayListOf()

    ) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.tvTitle)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val description = view.findViewById<TextView>(R.id.tvDescription)

        fun bind(article: Article){
            title.text = article.title
            description.text = article.description
            Glide.with(view.context).load(article.image).centerCrop().into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount() = dataset?.size ?: 0


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (dataset != null){
            //todo : check methode de verification
            holder.bind(dataset!!.get(position))
        }
    }

    fun setNotify(list: List<Article>){
        this.dataset = list
        //todo : check methode de notify, methode plus efficace pour check juste un seul élément
        notifyDataSetChanged()
    }
}