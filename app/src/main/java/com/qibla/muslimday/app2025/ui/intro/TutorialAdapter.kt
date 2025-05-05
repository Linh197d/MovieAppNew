package com.qibla.muslimday.app2025.ui.intro

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qibla.muslimday.app2025.databinding.ItemIntroBinding


class TutorialAdapter(
    var context: Context,
    private var listsIntro: MutableList<GuideModel>
) : RecyclerView.Adapter<TutorialAdapter.ViewHolderIntro>() {
    inner class ViewHolderIntro(private val binding: ItemIntroBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(guideModel: GuideModel) {
            binding.tvTitle.text = guideModel.title
            binding.tvContent.text = guideModel.content
            Glide.with(context).load(context.getDrawable(guideModel.img)).into(binding.imgGuide)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TutorialAdapter.ViewHolderIntro {
        val binding = ItemIntroBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolderIntro(binding)
    }

    override fun onBindViewHolder(holder: TutorialAdapter.ViewHolderIntro, position: Int) {
        holder.bind(listsIntro[position])

    }

    override fun getItemCount(): Int {
        return listsIntro.size
    }


}