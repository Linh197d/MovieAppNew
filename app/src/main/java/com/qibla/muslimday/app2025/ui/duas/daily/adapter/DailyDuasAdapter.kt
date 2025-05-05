package com.qibla.muslimday.app2025.ui.duas.daily.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.database.Duas.DuasEntity
import com.qibla.muslimday.app2025.databinding.ItemDailyDuasBinding
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class DailyDuasAdapter(
    val context: Context,
    val preferenceHelper: PreferenceHelper,
    private val listDataDetail: List<DuasEntity>,
    private val ionClickItemDailyDuas: IonClickItemDailyDuas
) : RecyclerView.Adapter<DailyDuasAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemDailyDuasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dailyDuasEntity: DuasEntity) {
            binding.tvTextDuas.text = dailyDuasEntity.fontName

            binding.tvPhoneticTranslation.text = dailyDuasEntity.textIndia
            binding.tvLanguageTranslation.text = context.getText(dailyDuasEntity.textEnglish)
            if (dailyDuasEntity.isCheck) {
                binding.llPreview1.background =
                    binding.root.resources.getDrawable(R.drawable.shape_bg_color_white_corner_16)
                binding.btnBookmark.setBackgroundResource(R.drawable.bg_btn_bookmark_seleced)
            } else {
                binding.llPreview1.background = null
                binding.btnBookmark.setBackgroundResource(R.drawable.bg_btn_bookmark_not_selected)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDailyDuasBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listDataDetail.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listDataDetail[position]
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
            ionClickItemDailyDuas.onClickBookMarkItemDailyDuas(item)
        }

        holder.binding.btnShare.setOnClickListener {
            ionClickItemDailyDuas.onClickShareItemDailyDuas(item)
        }
    }
}