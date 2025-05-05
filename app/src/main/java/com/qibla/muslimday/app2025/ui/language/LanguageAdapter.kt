package com.qibla.muslimday.app2025.ui.language

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.databinding.ItemLanguageBinding
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class LanguageAdapter @Inject constructor() :
    ListAdapter<LanguageModel, LanguageAdapter.LanguageViewHolder>(LanguageDiffCallback()) {
    private var isLoad = true

    private var onClickListener: IClickItemLanguage? = null

    fun setOnClickListener(onClickListener: IClickItemLanguage) {
        this.onClickListener = onClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding =
            ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(binding)
    }


    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    fun getSelectedLanguage(): LanguageModel? {
        return currentList.firstOrNull { it.active }
    }

    inner class LanguageViewHolder(val binding: ItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(languageModel: LanguageModel) {
            binding.txtName.text = languageModel.name
            if (languageModel.active) {
                binding.ivLanguageSelect.setImageResource(R.drawable.ic_radio_active)
                binding.layoutItem.background =
                    ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.shape_bg_color_white_stroke_primary_corner_16
                    )
            } else {
                binding.ivLanguageSelect.setImageResource(R.drawable.ic_radio_inactive)
                binding.layoutItem.background = ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.shape_bg_color_white_corner_16
                )
            }

            binding.layoutItem.setOnClickListener {
                if (isLoad) {
                    onClickListener?.onClickItemLanguage()
                    isLoad = false
                }
                resetChecked()
                languageModel.active = true
                notifyDataSetChanged()
            }
            when (languageModel.code) {
                "es" -> {
                    binding.icLang.setImageResource(R.drawable.ic_es)
                }

                "zh-rCN" -> {
                    binding.icLang.setImageResource(R.drawable.ic_zh)
                }

                "zh-rTW" -> {
                    binding.icLang.setImageResource(R.drawable.ic_zh)
                }

                "en" -> {
                    binding.icLang.setImageResource(R.drawable.ic_en)
                }

                "fr" -> {
                    binding.icLang.setImageResource(R.drawable.ic_fr)
                }

                "hi" -> {
                    binding.icLang.setImageResource(R.drawable.ic_hi)
                }

                "in" -> {
                    binding.icLang.setImageResource(R.drawable.ic_in)
                }

                "pt" -> {
                    binding.icLang.setImageResource(R.drawable.ic_pt)
                }

                "pt-rBR" -> {
                    binding.icLang.setImageResource(R.drawable.ic_pt_br)
                }

                "ar" -> {
                    binding.icLang.setImageResource(R.drawable.ic_ar)
                }

                "bn" -> {
                    binding.icLang.setImageResource(R.drawable.ic_bn)
                }

                "ru" -> {
                    binding.icLang.setImageResource(R.drawable.ic_ru)
                }

                "de" -> {
                    binding.icLang.setImageResource(R.drawable.ic_de)
                }

                "ja" -> {
                    binding.icLang.setImageResource(R.drawable.ic_ja)
                }

                "tr" -> {
                    binding.icLang.setImageResource(R.drawable.ic_tr)
                }

                "ko" -> {
                    binding.icLang.setImageResource(R.drawable.ic_ko)
                }
            }
        }

        private fun resetChecked() {
            currentList.forEach { it.active = false }
        }
    }
}

class LanguageDiffCallback : DiffUtil.ItemCallback<LanguageModel>() {
    override fun areItemsTheSame(oldItem: LanguageModel, newItem: LanguageModel): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: LanguageModel, newItem: LanguageModel): Boolean {
        return oldItem == newItem
    }


}