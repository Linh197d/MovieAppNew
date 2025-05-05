package com.qibla.muslimday.app2025.ui.ramadan.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.databinding.ItemFontBinding
import com.qibla.muslimday.app2025.ui.ramadan.model.FontModel

class FontAdapter(
    var context: Context,
    var listFont: ArrayList<FontModel>,
    var onClickItemFont: (FontModel, Int) -> Unit,
) :
    RecyclerView.Adapter<FontAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ItemFontBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fontModel: FontModel) {
            binding.tvNameFont.text = fontModel.fontName
            binding.tvNameFont.setTypeface(
                Typeface.createFromAsset(
                    context.assets,
                    "fonts/${fontModel.fontPath}"
                )
            )

            if (fontModel.isSelected) {
                binding.tvNameFont.setBackgroundResource(R.drawable.bg_btn_selected_font)
            } else {
                binding.tvNameFont.setBackgroundResource(R.drawable.bg_btn_un_selected_font)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFontBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listFont.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFont[position])

        holder.binding.root.setOnClickListener {

            deSelectAllItem()

            onClickItemFont.invoke(listFont[position], position)

            if (listFont[position].isSelected) {
                holder.binding.tvNameFont.setBackgroundResource(R.drawable.bg_btn_selected_font)
            } else {
                holder.binding.tvNameFont.setBackgroundResource(R.drawable.bg_btn_un_selected_font)
            }

            notifyItemChanged(position)

        }
    }

    fun deSelectAllItem() {
        val it = listFont.iterator()
        while (it.hasNext()) {
            val font: FontModel = it.next() as FontModel
            val position: Int =
                listFont.indexOf(
                    font
                )
            font.isSelected = false
            notifyItemChanged(position)
        }
    }
}