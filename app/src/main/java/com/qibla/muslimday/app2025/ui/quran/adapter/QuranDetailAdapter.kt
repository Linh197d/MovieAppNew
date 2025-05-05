package com.qibla.muslimday.app2025.ui.quran.adapter

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.databinding.ItemSurahQuranDetailBinding
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Verse
import com.qibla.muslimday.app2025.ui.quran.quranDetail.QuranDetailViewModel
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class QuranDetailAdapter(
    val context: Context,
    val viewModel: QuranDetailViewModel,
    val preferenceHelper: PreferenceHelper,
    private val listVerse: MutableList<Verse>,
    private val iOnClickItemQuranDetail: IOnClickItemQuranDetail,
) : RecyclerView.Adapter<QuranDetailAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemSurahQuranDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(verse: Verse) {
            Log.d("ntt", verse.toString())
            if (verse.isPlaySound) {
                binding.llPreview1.setBackgroundResource(R.drawable.bg_item_quran_isplaying)
            } else {
                binding.llPreview1.setBackgroundResource(R.color.transparent)
            }

            if (verse.isBookmark) {
                binding.btnBookmark.setImageResource(R.drawable.bg_btn_bookmark_seleced)
            } else {
                binding.btnBookmark.setImageResource(R.drawable.bg_btn_bookmark_not_selected)
            }

            if (verse.isShowTitle) {
                binding.tvTitle.visibility = View.VISIBLE
                binding.tvTitle.text = "Juz ${verse.juz_number}"
            } else {
                binding.tvTitle.visibility = View.GONE
            }

            val translationText = StringBuilder()

            val phoneticText = StringBuilder()

            for (word in verse.words!!) {
                val translation = word.translation!!.text ?: ""
                translationText.append(translation).append(" ")

                val phonetic = word.transliteration!!.text ?: ""
                phoneticText.append(phonetic).append(" ")
            }

            binding.tvLanguageTranslation.text = translationText.trim()

            binding.tvTextQuran.text = verse.text_uthmani

            binding.tvPhoneticTranslation.text = phoneticText.trim()

            binding.tvNumQuran.text = verse.verse_number.toString()

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(listVerse[position])

        holder.binding.root.setOnClickListener {
            iOnClickItemQuranDetail.onClickItemQuranDetail(
                listVerse[position],
                position
            )

        }

        holder.binding.btnShare.setOnClickListener {

            iOnClickItemQuranDetail.onClickShareItemQuranDetail(
                listVerse[position]
            )

        }

        holder.binding.btnBookmark.setOnClickListener {

            iOnClickItemQuranDetail.onClickBookMarkItemQuranDetail(
                listVerse[position]
            )
        }

        if (preferenceHelper.getFontStyle() == 0) {
            holder.binding.tvTextQuran.typeface = Typeface.createFromAsset(
                context.assets,
                "inter_regular.ttf"
            )

            holder.binding.tvLanguageTranslation.typeface = Typeface.createFromAsset(
                context.assets,
                "inter_regular.ttf"
            )

            holder.binding.tvPhoneticTranslation.typeface = Typeface.createFromAsset(
                context.assets,
                "inter_regular.ttf"
            )
        } else {
            holder.binding.tvTextQuran.typeface = Typeface.createFromAsset(
                context.assets,
                "uthmanic_hafs.otf"
            )

            holder.binding.tvLanguageTranslation.typeface = Typeface.createFromAsset(
                context.assets,
                "uthmanic_hafs.otf"
            )

            holder.binding.tvPhoneticTranslation.typeface = Typeface.createFromAsset(
                context.assets,
                "uthmanic_hafs.otf"
            )
        }

        holder.binding.tvTextQuran.textSize = preferenceHelper.getFontSize().toFloat()
        holder.binding.tvTranslation.textSize = (preferenceHelper.getFontSize() - 5).toFloat()
        holder.binding.tvLanguageTranslation.textSize = preferenceHelper.getFontSize().toFloat()
        holder.binding.tvPhoneticTranslation.textSize = preferenceHelper.getFontSize().toFloat()

        if (preferenceHelper.getIsPhoneticTranslation()) {
            holder.binding.tvPhoneticTranslation.visibility = View.VISIBLE
        } else {
            holder.binding.tvPhoneticTranslation.visibility = View.GONE
        }

        if (preferenceHelper.getIsLanguageTranslation()) {
            holder.binding.tvLanguageTranslation.visibility = View.VISIBLE
            holder.binding.tvTranslation.visibility = View.VISIBLE
        } else {
            holder.binding.tvLanguageTranslation.visibility = View.GONE
            holder.binding.tvTranslation.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSurahQuranDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listVerse.size
    }

    fun resetChecked() {
        listVerse.forEach { it.isPlaySound = false }
        notifyDataSetChanged()
    }

}