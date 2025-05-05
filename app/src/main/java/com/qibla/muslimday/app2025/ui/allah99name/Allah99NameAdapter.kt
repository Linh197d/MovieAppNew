package com.qibla.muslimday.app2025.ui.allah99name

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qibla.muslimday.app2025.databinding.Field99AllahItemBinding
import com.qibla.muslimday.app2025.model.Allah99Name

class Allah99NameAdapter : RecyclerView.Adapter<Allah99NameAdapter.Allah99VH>() {

    private var dataList: MutableList<Allah99Name> = arrayListOf()

    class Allah99VH(private val binding: Field99AllahItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Allah99Name) {
            val position = bindingAdapterPosition + 1
            binding.tvFiled99Allah.text = if (position < 10) {
                "0$position"
            } else {
                position.toString()
            }
            binding.tvField99AllahMening.text = data.meaning?.trim()
            binding.tvField99AllahTransliteration.text = data.transliteration?.trim()
            binding.tvField99AllahName.text = data.name?.trim()
            binding.tvField99AllahMening.isSelected = true
            binding.tvField99AllahTransliteration.isSelected = true
            binding.tvField99AllahName.isSelected = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Allah99VH {
        return Allah99VH(
            Field99AllahItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: Allah99VH, position: Int) {
        holder.onBind(dataList[position])
    }

    fun addList(list: List<Allah99Name>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }
}