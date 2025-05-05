package com.qibla.muslimday.app2025.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.model.ItemAzkarCategoryModel

class AzkarChildAdapter(
    private val childAzkarList: List<ItemAzkarCategoryModel>,
    private val onChildItemClickListener: OnChildItemClickListener
) :
    RecyclerView.Adapter<AzkarChildAdapter.ChildViewHolder>() {
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
        return childAzkarList.size
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.content.setText(childAzkarList[position].title)
        holder.line.visibility = View.GONE
        holder.content.isSelected = true
        holder.content.setOnClickListener {
            onChildItemClickListener.onChildItemClick(childAzkarList[position])
        }
    }

    interface OnChildItemClickListener {
        fun onChildItemClick(childItem: ItemAzkarCategoryModel)
    }
}