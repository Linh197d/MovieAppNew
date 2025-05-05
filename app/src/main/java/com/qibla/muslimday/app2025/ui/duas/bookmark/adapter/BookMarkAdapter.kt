package com.qibla.muslimday.app2025.ui.duas.bookmark.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.database.Duas.DuasEntity
import com.qibla.muslimday.app2025.helper.PreferenceHelper


class BookMarkAdapter(
    private val content: Context,
    private val listItemModel: List<Pair<Int, List<DuasEntity>>>,
    private val listDuas: List<String>,
    val preferenceHelper: PreferenceHelper,
    private val onChildItemClickListener: BookMarkChildAdapter.OnChildItemClickListener
) : Adapter<BookMarkAdapter.BookMarkViewHolder>() {
    inner class BookMarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.tv_name)
        val childRecyclerView: RecyclerView = itemView.findViewById(R.id.rcv_duas_child)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookMarkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_title_book_mark_duas, parent, false)
        return BookMarkViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItemModel.size
    }

    override fun onBindViewHolder(holder: BookMarkViewHolder, position: Int) {
        val item = listItemModel[position]
        holder.textTitle.text = listDuas[listItemModel[position].first - 1]
        holder.childRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
        val childAdapter =
            BookMarkChildAdapter(content, item.second, preferenceHelper, onChildItemClickListener)
        holder.childRecyclerView.adapter = childAdapter
    }
}