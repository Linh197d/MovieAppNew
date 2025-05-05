package com.qibla.muslimday.app2025.ui.quran.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.database.Quran.QuranEntity
import com.qibla.muslimday.app2025.databinding.ItemSurahBinding

class BookMarkAdapter(
    private val listDataQuran: List<QuranEntity>,
    private val iClickItemQuran: IClickItemQuran,
) :
    RecyclerView.Adapter<BookMarkAdapter.ViewHolder>() {

    var listQuranFiltered = ArrayList<QuranEntity>()

    init {
        listQuranFiltered.addAll(listDataQuran)
        notifyDataSetChanged()
    }


    class ViewHolder(var binding: ItemSurahBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: QuranEntity) {
            binding.tvName.text = data.name
            binding.tvNum.text = data.num.toString()
            binding.tvNameTranslation.text = data.translation
            binding.imgBookmark.setImageResource(R.drawable.bg_btn_bookmark_seleced)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSurahBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listQuranFiltered.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listQuranFiltered[position])

        holder.binding.llText.setOnClickListener {
            iClickItemQuran.onClickItemQuran(listQuranFiltered[position])
        }


        holder.binding.imgBookmark.setOnClickListener {
            iClickItemQuran.onClickBookMarkItemQuran(listQuranFiltered[position], position)
        }
    }
}