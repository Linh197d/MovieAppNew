package com.qibla.muslimday.app2025.ui.azkar.bookmark.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.database.Azkar.AzkarEntity
import com.qibla.muslimday.app2025.helper.PreferenceHelper

class BookMarkAzkarChildAdapter(
    val content: Context,
    private val listItemModel: List<AzkarEntity>,
    val preferenceHelper: PreferenceHelper,
    private val onChildItemClickListener: OnChildItemClickListener
) : RecyclerView.Adapter<BookMarkAzkarChildAdapter.ChildViewHolder>() {
    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textDuas: TextView = itemView.findViewById(R.id.tv_text_duas)
        val textPhoneTic: TextView = itemView.findViewById(R.id.tv_phonetic_translation)
        val textLanguage: TextView = itemView.findViewById(R.id.tv_language_translation)
        val tvTranslation: TextView = itemView.findViewById(R.id.tv_translation)
        val btnBookMark: ImageView = itemView.findViewById(R.id.btn_bookmark)
        val btnShare: ImageView = itemView.findViewById(R.id.btn_share)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_content_book_mark_azkar, parent, false)
        return ChildViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItemModel.size
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val childItem = listItemModel[position]
        holder.textDuas.text = childItem.textArabic
        holder.textPhoneTic.text = childItem.textIndia
        holder.textLanguage.text = content.getString(childItem.textEnglish)

        if (preferenceHelper.getFontStyle() == 0) {
            holder.textDuas.typeface = Typeface.createFromAsset(
                content.assets,
                "inter_regular.ttf"
            )

            holder.textLanguage.typeface = Typeface.createFromAsset(
                content.assets,
                "inter_regular.ttf"
            )

            holder.textPhoneTic.typeface = Typeface.createFromAsset(
                content.assets,
                "inter_regular.ttf"
            )
        } else {
            holder.textDuas.typeface = Typeface.createFromAsset(
                content.assets,
                "uthmanic_hafs.otf"
            )

            holder.textLanguage.typeface = Typeface.createFromAsset(
                content.assets,
                "uthmanic_hafs.otf"
            )

            holder.textPhoneTic.typeface = Typeface.createFromAsset(
                content.assets,
                "uthmanic_hafs.otf"
            )
        }

//        holder.textDuas.textSize = preferenceHelper.getFontSize().toFloat()
//        holder.tvTranslation.textSize = (preferenceHelper.getFontSize() - 5).toFloat()
//        holder.textLanguage.textSize = preferenceHelper.getFontSize().toFloat()
//        holder.textPhoneTic.textSize = preferenceHelper.getFontSize().toFloat()

        holder.btnBookMark.setOnClickListener {
            onChildItemClickListener.onChildItemClick(childItem)
        }
        holder.btnShare.setOnClickListener {
            onChildItemClickListener.onChildItemShareClick(childItem)
        }
    }

    interface OnChildItemClickListener {
        fun onChildItemClick(childItem: AzkarEntity)
        fun onChildItemShareClick(childItem: AzkarEntity)
    }
}