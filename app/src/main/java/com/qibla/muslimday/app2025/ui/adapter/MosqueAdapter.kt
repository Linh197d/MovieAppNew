package com.qibla.muslimday.app2025.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.network.model_mosque.MosqueWithImage
import com.qibla.muslimday.app2025.util.SystemUtil.dpToPx

class MosqueAdapter(
    private val context: Context,
    private var mosqueList: List<MosqueWithImage>, // ✅ Chuyển từ `val` -> `var`
    private val onChildItemClickListener: OnChildItemClickListener
) : RecyclerView.Adapter<MosqueAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name_mosque)
        val address: TextView = itemView.findViewById(R.id.address_mosque)
        val distance: TextView = itemView.findViewById(R.id.distance)
        val findButton: TextView = itemView.findViewById(R.id.open_ggmap)
        val imgMosque: ImageView = itemView.findViewById(R.id.imgMosque)
    }

    // ✅ Cập nhật danh sách mới và làm mới RecyclerView
    fun updateData(newMosques: List<MosqueWithImage>) {
        mosqueList = newMosques // ✅ Gán danh sách mới
        notifyDataSetChanged() // ✅ Cập nhật RecyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mosque, parent, false)
        return ChildViewHolder(view)
    }

    override fun getItemCount(): Int = mosqueList.size

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val data = mosqueList[position]
        holder.name.text = data.name
        holder.address.text =
            data.address.ifEmpty { "Địa chỉ không có sẵn" } // ✅ Hiển thị fallback nếu không có địa chỉ
        holder.distance.text = data.distance
            ?: "Không xác định" // ✅ Nếu có khoảng cách thì hiển thị, không có thì mặc định

        // ✅ Kiểm tra xem có ảnh không, nếu không có thì ẩn ImageView
        if (!data.imageUrl.isNullOrEmpty()) {
            holder.imgMosque.visibility = View.VISIBLE
            Glide.with(context).load(data.imageUrl)
                .apply(RequestOptions().transform(RoundedCorners(12.dpToPx())))
                .into(holder.imgMosque)
        } else {
            holder.imgMosque.setImageResource(R.drawable.img_default_mosque)
        }

        holder.findButton.setOnClickListener {
            onChildItemClickListener.onChildItemClick(data)
        }
    }

    interface OnChildItemClickListener {
        fun onChildItemClick(childItem: MosqueWithImage)
    }
}
