package com.qibla.muslimday.app2025.ui.azkar.bookmark.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.database.Azkar.AzkarEntity
import com.qibla.muslimday.app2025.helper.PreferenceHelper

class BookMarkAzkarAdapter(
    private val content: Context,
    private val listItemModel: List<Pair<Int, List<AzkarEntity>>>,
    private val listItem: List<String>,
    val preferenceHelper: PreferenceHelper,
    private val onChildItemClickListener: BookMarkAzkarChildAdapter.OnChildItemClickListener
) : RecyclerView.Adapter<BookMarkAzkarAdapter.BookMarkViewHolder>() {
    inner class BookMarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.tv_name)
        val childRecyclerView: RecyclerView = itemView.findViewById(R.id.rcv_duas_child)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookMarkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_title_book_mark_azkar, parent, false)
        return BookMarkViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItemModel.size
    }

    override fun onBindViewHolder(holder: BookMarkViewHolder, position: Int) {
        Log.d("cvcv", "" + listItemModel)
        Log.d("cvcv", "" + listItemModel[position])
        Log.d("cvcv", "" + listItemModel[position].first)
        Log.d("cvcv", "" + listItemModel[position].second)
        Log.d("cvcv", "" + listItem)
        val item = listItemModel[position]
        holder.textTitle.text = listItem[listItemModel[position].first - 1]
        Log.d("cvcv", "" + item.second)
        holder.childRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
        val childAdapter = BookMarkAzkarChildAdapter(
            content,
            item.second,
            preferenceHelper,
            onChildItemClickListener
        )
        holder.childRecyclerView.adapter = childAdapter
    }
}