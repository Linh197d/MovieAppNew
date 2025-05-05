package com.qibla.muslimday.app2025.ui.ramadan.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qibla.muslimday.app2025.databinding.ItemColorBinding
import com.qibla.muslimday.app2025.extensions.createCircleDrawable
import com.qibla.muslimday.app2025.ui.ramadan.model.ColorModel

class ColorAdapter(
    private var colors: ArrayList<ColorModel>,
    var onClickItemColor: (ColorModel, Int) -> Unit,
) :
    RecyclerView.Adapter<ColorAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ItemColorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(colorModel: ColorModel) {
            if (colorModel.isSelected) {
                binding.viewSelectedColor.visibility = View.VISIBLE
            } else {
                binding.viewSelectedColor.visibility = View.INVISIBLE
            }

            binding.imgColor.background = createCircleDrawable(colorModel.colorCode)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemColorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return colors.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(colors[position])

        holder.binding.root.setOnClickListener {

            deSelectAllItem()

            onClickItemColor.invoke(colors[position], position)

            if (colors[position].isSelected) {
                holder.binding.viewSelectedColor.visibility = View.VISIBLE
            } else {
                holder.binding.viewSelectedColor.visibility = View.INVISIBLE
            }

            notifyItemChanged(position)
        }
    }

    fun deSelectAllItem() {
        Log.d("ttnn", "deSelectAllItem")
        val it = colors.iterator()
        while (it.hasNext()) {
            val color: ColorModel = it.next() as ColorModel
            val position: Int =
                colors.indexOf(
                    color
                )
            color.isSelected = false
            notifyItemChanged(position)
        }
    }
}