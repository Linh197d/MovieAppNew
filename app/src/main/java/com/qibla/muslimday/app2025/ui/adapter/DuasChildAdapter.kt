package com.qibla.muslimday.app2025.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.model.ItemDuasChildModel

class DuasChildAdapter(
    private val childDuasList: List<ItemDuasChildModel>,
    private val onChildItemClickListener: OnChildItemClickListener
) : RecyclerView.Adapter<DuasChildAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val content: TextView = itemView.findViewById(R.id.tv_item_child_content)

        val line: View = itemView.findViewById(R.id.divine_line)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_azkar_child, parent, false)
        return ChildViewHolder(view)
    }

    override fun getItemCount(): Int {
        return childDuasList.size
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val childItem = childDuasList[position]
        holder.content.text = childItem.title
        holder.itemView.isVisible = childItem.isShow

//        if (position == childDuasList.size - 1) {
//            holder.line.visibility = View.GONE
//        } else {
//            holder.line.visibility = View.VISIBLE
//        }

        holder.itemView.setOnClickListener {
            onChildItemClickListener.onChildItemClick(childItem)
        }
    }

    interface OnChildItemClickListener {
        fun onChildItemClick(childItem: ItemDuasChildModel)
    }
}