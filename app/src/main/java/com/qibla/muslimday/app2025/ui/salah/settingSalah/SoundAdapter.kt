package com.qibla.muslimday.app2025.ui.salah.settingSalah

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.databinding.ItemSoundSettingBinding
import com.qibla.muslimday.app2025.model.Sound

class SoundAdapter(
    var listSound: ArrayList<Sound>,
    var iClickItemSoundSetting: IClickItemSoundSetting,
) : RecyclerView.Adapter<SoundAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemSoundSettingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sound: Sound) {

            binding.tvNameSound.isSelected = true

            if (sound.id == 0) {
                binding.imgSound.setImageResource(R.drawable.ic_silent)
            } else {
                binding.imgSound.setImageResource(R.drawable.ic_sound)
            }

            binding.tvNameSound.text = sound.name

            if (sound.isChecked) {
                binding.imgCheck.visibility = View.VISIBLE
            } else {
                binding.imgCheck.visibility = View.INVISIBLE
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSoundSettingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listSound.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listSound[position])

        holder.binding.root.setOnClickListener {
            resetChecked()
            listSound[position].isChecked = true
            notifyDataSetChanged()
            iClickItemSoundSetting.onClickItemSound(listSound[position])
        }
    }

    private fun resetChecked() {
        listSound.forEach { it.isChecked = false }
    }
}