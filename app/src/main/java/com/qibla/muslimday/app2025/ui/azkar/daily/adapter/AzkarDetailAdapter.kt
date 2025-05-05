package com.qibla.muslimday.app2025.ui.azkar.daily.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.database.Azkar.AzkarEntity
import com.qibla.muslimday.app2025.databinding.ItemDailyAzkarBinding
import com.qibla.muslimday.app2025.helper.PreferenceHelper

class AzkarDetailAdapter(
    val context: Context,
    val preferenceHelper: PreferenceHelper,
    private val listAzkar: List<AzkarEntity>,
    private val ionClickItem: IonClickItemAzkarDetail
) : RecyclerView.Adapter<AzkarDetailAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemDailyAzkarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(azkarEntity: AzkarEntity) {
            binding.tvTextDuas.text = azkarEntity.textArabic

            binding.tvPhoneticTranslation.text = azkarEntity.textIndia
            binding.tvLanguageTranslation.text = context.getText(azkarEntity.textEnglish)
            if (azkarEntity.isCheck) {
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_active)
                binding.llPreview1.background =
                    binding.root.resources.getDrawable(R.drawable.shape_bg_color_white_corner_16)
            } else {
                binding.llPreview1.background = null
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_inactive)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDailyAzkarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listAzkar.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listAzkar[position]
        holder.bind(item)

        if (preferenceHelper.getFontStyle() == 0) {
            holder.binding.tvTextDuas.typeface = Typeface.createFromAsset(
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
            holder.binding.tvTextDuas.typeface = Typeface.createFromAsset(
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

//        holder.binding.tvTextDuas.textSize = preferenceHelper.getFontSize().toFloat()
//        holder.binding.tvTranslation.textSize = (preferenceHelper.getFontSize() - 5).toFloat()
//        holder.binding.tvLanguageTranslation.textSize = preferenceHelper.getFontSize().toFloat()
//        holder.binding.tvPhoneticTranslation.textSize = preferenceHelper.getFontSize().toFloat()


        holder.binding.btnBookmark.setOnClickListener {
            ionClickItem.onClickBookMarkItem(item)
        }
        holder.binding.btnShare.setOnClickListener {
            ionClickItem.onClickShareItem(item)
        }
    }
}