package com.qibla.muslimday.app2025.ui.zakat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.databinding.ItemCurrencyUnitBinding
import com.qibla.muslimday.app2025.model.CurrencyUnitModel
import com.qibla.muslimday.app2025.ui.language.IClickItemLanguage
import javax.inject.Inject

class CurrencyAdapter @Inject constructor() :
    ListAdapter<CurrencyUnitModel, CurrencyAdapter.LanguageViewHolder>(LanguageDiffCallback()) {
    private var isLoad = true

    private var onClickListener: IClickItemLanguage? = null

    fun setOnClickListener(onClickListener: IClickItemLanguage) {
        this.onClickListener = onClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding =
            ItemCurrencyUnitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(binding)
    }


    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    fun getSelectedLanguage(): CurrencyUnitModel? {
        return currentList.firstOrNull { it.active }
    }

    inner class LanguageViewHolder(val binding: ItemCurrencyUnitBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: CurrencyUnitModel) {
            binding.txtName.text = data.code
            binding.nameUnit.text = data.name
            if (data.active) {
                binding.layoutItem.background =
                    ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.shape_bg_color_white_stroke_primary_corner_16
                    )
            } else {
                binding.layoutItem.background = ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.shape_bg_color_white_corner_16
                )
            }

            binding.layoutItem.setOnClickListener {
                resetChecked()
                data.active = true
                notifyDataSetChanged()
            }
            when (data.code) {
                "GBP" -> {
                    Glide.with(binding.root.context).asBitmap()
                        .load(R.drawable.ic_english_flag)
                        .into(binding.icLang)
                }

                "EUR" -> {
                    Glide.with(binding.root.context).asBitmap()
                        .load(R.drawable.ic_eur)
                        .into(binding.icLang)
                }

                "AUD" -> {
                    Glide.with(binding.root.context).asBitmap()
                        .load(R.drawable.ic_aud)
                        .into(binding.icLang)
                }

                "USD" -> {
                    Glide.with(binding.root.context).asBitmap()
                        .load(R.drawable.ic_usd)
                        .into(binding.icLang)
                }

                "CNY" -> {
                    Glide.with(binding.root.context).asBitmap()
                        .load(R.drawable.ic_cny)
                        .into(binding.icLang)
                }

                "INR" -> {
                    Glide.with(binding.root.context).asBitmap()
                        .load(R.drawable.ic_inr)
                        .into(binding.icLang)
                }

                "VND" -> {
                    Glide.with(binding.root.context).asBitmap()
                        .load(R.drawable.ic_vnd)
                        .into(binding.icLang)
                }

                "THB" -> {
                    Glide.with(binding.root.context).asBitmap()
                        .load(R.drawable.ic_thb)
                        .into(binding.icLang)
                }

                "IDR" -> {
                    Glide.with(binding.root.context).asBitmap()
                        .load(R.drawable.ic_idr)
                        .into(binding.icLang)
                }

            }
        }

        private fun resetChecked() {
            currentList.forEach { it.active = false }
        }
    }
}

class LanguageDiffCallback : DiffUtil.ItemCallback<CurrencyUnitModel>() {
    override fun areItemsTheSame(oldItem: CurrencyUnitModel, newItem: CurrencyUnitModel): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(
        oldItem: CurrencyUnitModel,
        newItem: CurrencyUnitModel
    ): Boolean {
        return oldItem == newItem
    }


}