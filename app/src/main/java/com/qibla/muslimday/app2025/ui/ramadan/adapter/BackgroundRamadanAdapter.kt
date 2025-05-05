package com.qibla.muslimday.app2025.ui.ramadan.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.qibla.muslimday.app2025.databinding.ItemBackgroundRamadanBinding
import com.qibla.muslimday.app2025.ui.ramadan.model.BackgroundRamadan

class BackgroundRamadanAdapter(
    var context: Context,
    var listBackgroundRamadan: ArrayList<BackgroundRamadan>,
    var onClickItemBackground: (BackgroundRamadan, Int) -> Unit,
) : RecyclerView.Adapter<BackgroundRamadanAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ItemBackgroundRamadanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(backgroundRamadan: BackgroundRamadan) {
            Glide.with(context)
                .load(Uri.parse(backgroundRamadan.pathBackground))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgBackground)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBackgroundRamadanBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listBackgroundRamadan.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listBackgroundRamadan[position])

        holder.binding.root.setOnClickListener {
            onClickItemBackground.invoke(listBackgroundRamadan[position], position)
        }
    }
}