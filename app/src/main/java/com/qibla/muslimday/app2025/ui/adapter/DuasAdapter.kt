package com.qibla.muslimday.app2025.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.nlbn.ads.callback.NativeCallback
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.model.ItemDuasModel
import com.qibla.muslimday.app2025.util.AdsInter

class DuasAdapter(
    val lifecycle: LifecycleCoroutineScope,
    val context: Context,
    val listDuasModel: List<ItemDuasModel>,
    private val onChildItemClickListener: DuasChildAdapter.OnChildItemClickListener
) : Adapter<RecyclerView.ViewHolder>() {

    var onClick: (ItemDuasModel) -> Unit = {}

    inner class DuasParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconDuas: ImageView = itemView.findViewById(R.id.ic_item_duas)
        val titleDuas: TextView = itemView.findViewById(R.id.tv_title_item_duas)
        val childRecyclerView: RecyclerView = itemView.findViewById(R.id.rcv_duas_child)
        val titleItem: ConstraintLayout = itemView.findViewById(R.id.item_duas_tittle)
        val iconArrow: ImageView = itemView.findViewById(R.id.img_ic_arrow_right)
        val root: ConstraintLayout = itemView.findViewById(R.id.clDuaRoot)
        var isChildVisible = false
    }

    inner class AdsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var frAds: FrameLayout

        init {
            frAds = itemView.findViewById(R.id.fr_ads)
        }
    }

    val TYPE_ADS = 2
    val TYPE_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_duas,parent,false)
//        return DuasParentViewHolder(view)

        if (context.hasNetworkConnection()) {
            //internet ol
            if (viewType == TYPE_ADS) {
                return AdsViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_layout_ads_native, parent, false)
                )
            } else {
                return DuasParentViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_duas, parent, false)
                )
            }
        } else {
            //internet off
            return DuasParentViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.item_duas, parent, false)
            )
        }
    }


    override fun getItemCount(): Int {
        return listDuasModel.size
    }

    override fun getItemViewType(position: Int): Int {
        if (listDuasModel[position].title == "Ads") {
            return TYPE_ADS
        } else {
            return TYPE_ITEM
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (context.hasNetworkConnection()) {
            //internet ol
            if (holder is DuasParentViewHolder) {
                var viewHolder: DuasParentViewHolder = holder

                val parentItem = listDuasModel[position]
                viewHolder.iconDuas.setImageResource(parentItem.icBackground)
                viewHolder.titleDuas.setText(parentItem.title)
                viewHolder.childRecyclerView.layoutManager =
                    LinearLayoutManager(viewHolder.itemView.context)
                val childAdapter =
                    DuasChildAdapter(listDuasModel[position].listDuas, onChildItemClickListener)
                viewHolder.childRecyclerView.adapter = childAdapter

                viewHolder.titleItem.setOnClickListener {
                    onClick.invoke(listDuasModel[position])
                }

                viewHolder.childRecyclerView.visibility =
                    if (viewHolder.isChildVisible) View.VISIBLE else View.GONE


            } else {

                var viewHolder: AdsViewHolder = holder as AdsViewHolder
                //ads native

                loadAdsNativeDuas(position, viewHolder, listDuasModel[position])

//                try {
//                    if (ConsentHelper.getInstance(context).canRequestAds()) {
//                        Admob.getInstance().loadNativeAd(
//                            context,
//                            context.getString(R.string.native_duas),
//                            object : NativeCallback() {
//                                override fun onNativeAdLoaded(nativeAd: NativeAd?) {
//                                    super.onNativeAdLoaded(nativeAd)
//                                    val adView: NativeAdView = LayoutInflater.from(context)
//                                        .inflate(
//                                            R.layout.layout_ads_native_update,
//                                            null
//                                        ) as NativeAdView
//                                    viewHolder.frAds.removeAllViews()
//                                    viewHolder.frAds.addView(adView)
//                                    Admob.getInstance().pushAdsToViewCustom(nativeAd, adView)
//                                }
//
//                                override fun onAdFailedToLoad() {
//                                    super.onAdFailedToLoad()
//                                    viewHolder.frAds.removeAllViews()
//                                }
//                            }
//                        )
//                    }
//                } catch (e: Exception) {
//                    viewHolder.frAds.removeAllViews()
//                }

                //end
            }
        } else {
            //internet off
            if (holder is DuasParentViewHolder) {
                var viewHolder: DuasParentViewHolder = holder
                val parentItem = listDuasModel[position]
                viewHolder.iconDuas.setImageResource(parentItem.icBackground)
                viewHolder.titleDuas.setText(parentItem.title)
                viewHolder.childRecyclerView.layoutManager =
                    LinearLayoutManager(viewHolder.itemView.context)
                val childAdapter =
                    DuasChildAdapter(listDuasModel[position].listDuas, onChildItemClickListener)
                viewHolder.childRecyclerView.adapter = childAdapter

                viewHolder.titleItem.setOnClickListener {
                    onClick.invoke(listDuasModel[position])
                }

                viewHolder.childRecyclerView.visibility =
                    if (viewHolder.isChildVisible) View.VISIBLE else View.GONE


            }
        }

//        val parentItem = listDuasModel[position]
//        holder.iconDuas.setImageResource(parentItem.icBackground)
//        holder.titleDuas.setText(parentItem.title)
//        holder.childRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
//        val childAdapter = DuasChildAdapter(listDuasModel[position].listDuas,onChildItemClickListener)
//        holder.childRecyclerView.adapter = childAdapter
//
//        holder.titleItem.setOnClickListener {
//            holder.isChildVisible = !holder.isChildVisible
//            val rotationAngle = if (holder.isChildVisible) 90f else 0f
//            val rotationAnimator = ObjectAnimator.ofFloat(holder.iconArrow, "rotation", rotationAngle)
//            rotationAnimator.duration = 300L
//            rotationAnimator.start()
//            holder.childRecyclerView.visibility = if (holder.isChildVisible) View.VISIBLE else View.GONE
//        }
//
//        holder.childRecyclerView.visibility = if (holder.isChildVisible) View.VISIBLE else View.GONE

    }

    private fun loadAdsNativeDuas(
        position: Int,
        viewHolder: DuasAdapter.AdsViewHolder,
        itemDuasModel: ItemDuasModel
    ) {
        Log.d("ntt", "loadAdsNativeDuas: ")

        when (position) {
            2 -> {
                try {
                    if (ConsentHelper.getInstance(context)
                            .canRequestAds() && AdsInter.isLoadNativeData1
                    ) {

                        if (AdsInter.nativeAdsData1 != null) {
                            AdsInter.nativeAdsData1?.let {
                                pushAdsToView(it, viewHolder.frAds)
                            }

                        } else {
                            Admob.getInstance().loadNativeAdWithAutoReloadWhenClick(
                                context,
                                context.getString(R.string.native_data1),
                                object : NativeCallback() {
                                    override fun onNativeAdLoaded(nativeAd: NativeAd) {
                                        pushAdsToView(nativeAd, viewHolder.frAds)
                                        AdsInter.nativeAdsData1 = nativeAd
                                    }

                                    override fun onAdFailedToLoad() {
                                        viewHolder.frAds.removeAllViews()
                                    }
//                                    override fun onAdImpression() {
//                                        super.onAdImpression()
//                                        AdsInter.nativeAdsData1=null
//                                    }
                                }
                            )
                        }
                    } else {
                        viewHolder.frAds.removeAllViews()
                        viewHolder.frAds.visibility = View.GONE
                    }
                } catch (e: Exception) {
                    viewHolder.frAds.removeAllViews()
                }
            }

            7 -> {
                try {
                    if (ConsentHelper.getInstance(context)
                            .canRequestAds() && AdsInter.isLoadNativeData2
                    ) {

                        if (AdsInter.nativeAdsData2 != null) {
                            AdsInter.nativeAdsData2?.let {
                                pushAdsToView(it, viewHolder.frAds)
                            }

                        } else {
                            Admob.getInstance().loadNativeAdWithAutoReloadWhenClick(
                                context,
                                context.getString(R.string.native_data2),
                                object : NativeCallback() {
                                    override fun onNativeAdLoaded(nativeAd: NativeAd) {
                                        pushAdsToView(nativeAd, viewHolder.frAds)
                                        AdsInter.nativeAdsData2 = nativeAd
                                    }

                                    override fun onAdFailedToLoad() {
                                        viewHolder.frAds.removeAllViews()
                                    }

//                                    override fun onAdImpression() {
//                                        super.onAdImpression()
//                                        AdsInter.nativeAdsData2=null
//                                    }
                                }
                            )
                        }
                    } else {
                        viewHolder.frAds.removeAllViews()
                        viewHolder.frAds.visibility = View.GONE
                    }
                } catch (e: Exception) {
                    viewHolder.frAds.removeAllViews()
                }
            }


        }
    }

    private fun pushAdsToView(nativeAd: NativeAd, frNativeAds: FrameLayout) {
        val adView = LayoutInflater.from(context)
            .inflate(R.layout.layout_ads_native_update, null)
        val nativeAdView = adView as NativeAdView
        frNativeAds.removeAllViews()
        frNativeAds.addView(adView)

        Admob.getInstance().pushAdsToViewCustom(nativeAd, nativeAdView)

    }

}