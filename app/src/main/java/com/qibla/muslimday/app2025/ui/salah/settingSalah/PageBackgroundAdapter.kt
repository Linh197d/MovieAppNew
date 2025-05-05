package com.qibla.muslimday.app2025.ui.salah.settingSalah

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qibla.muslimday.app2025.databinding.ItemPageBackgroundBinding
import com.qibla.muslimday.app2025.model.PageBackground

class PageBackgroundAdapter(
    var listPageBackground: ArrayList<PageBackground>,
    var iOnClickItemPageBackground: IClickItemPageBackground
) :
    RecyclerView.Adapter<PageBackgroundAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemPageBackgroundBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pageBackground: PageBackground) {
            binding.imgPageBackground.setImageResource(pageBackground.image)

            if (pageBackground.isChecked) {
                binding.imgTickClick.visibility = View.VISIBLE
            } else {
                binding.imgTickClick.visibility = View.GONE
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPageBackgroundBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listPageBackground.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPageBackground[position])

        holder.binding.root.setOnClickListener {
            resetChecked()
            listPageBackground[position].isChecked = true
            notifyDataSetChanged()
            iOnClickItemPageBackground.onClickItemPageBackground(listPageBackground[position])
        }
    }

    private fun resetChecked() {
        listPageBackground.forEach { it.isChecked = false }
    }
}