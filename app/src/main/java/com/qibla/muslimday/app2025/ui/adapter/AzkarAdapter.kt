package com.qibla.muslimday.app2025.ui.adapter

import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.nlbn.ads.callback.NativeCallback
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.model.ItemAzkarTopicModel
import com.qibla.muslimday.app2025.util.AdsInter

class AzkarAdapter(
    val lifecycle: LifecycleCoroutineScope,
    val context: Context,
    val azkarParentList: List<ItemAzkarTopicModel>,
    private val onChildItemClickListener: AzkarChildAdapter.OnChildItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var seeSub: (ItemAzkarTopicModel) -> Unit = {}

    private val isChildVisibleList = MutableList(azkarParentList.size) { false }

    inner class AzkarParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconAzkar: ImageView = itemView.findViewById(R.id.ic_item_azkar)
        val titleAzkar: TextView = itemView.findViewById(R.id.tv_title_item_azkar)
        val childRecyclerView: RecyclerView = itemView.findViewById(R.id.rcv_azkar_child)
        val titleItem: ConstraintLayout = itemView.findViewById(R.id.cl_azkar_item)
        val iconArrow: ImageView = itemView.findViewById(R.id.img_ic_arrow_right)
        val root: ConstraintLayout = itemView.findViewById(R.id.clAzkarRoot)
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

        if (context.hasNetworkConnection()) {
            //internet ol
            if (viewType == TYPE_ADS) {
                return AdsViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_layout_ads_native, parent, false)
                )
            } else {
                return AzkarParentViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_azkar, parent, false)
                )
            }
        } else {
            //internet off
            return AzkarParentViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.item_azkar, parent, false)
            )
        }
    }

    override fun getItemCount(): Int {
        return azkarParentList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (azkarParentList[position].title == "Ads") {
            return TYPE_ADS
        } else {
            return TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (context.hasNetworkConnection()) {
            //internet ol
            if (holder is AzkarParentViewHolder) {
                var viewHolder: AzkarParentViewHolder = holder

                val parentItem = azkarParentList[position]
                viewHolder.iconAzkar.setImageResource(parentItem.icBackground)
                viewHolder.titleAzkar.text = parentItem.title
                viewHolder.childRecyclerView.layoutManager =
                    LinearLayoutManager(viewHolder.itemView.context)
                val childAdapter =
                    AzkarChildAdapter(azkarParentList[position].listAzkar, onChildItemClickListener)
                viewHolder.childRecyclerView.adapter = childAdapter
                viewHolder.titleItem.setOnClickListener {
                    seeSub.invoke(azkarParentList[position])
                }
                viewHolder.childRecyclerView.isVisible = false
                val rotationAngle = if (azkarParentList[position].isChildVisible) 90f else 0f
                val rotationAnimator =
                    ObjectAnimator.ofFloat(viewHolder.iconArrow, "rotation", rotationAngle)
                rotationAnimator.duration = 0L
                rotationAnimator.start()


            } else {
                var viewHolder: AdsViewHolder = holder as AdsViewHolder
                //ads native
                loadAdsNativeAzkar(position, viewHolder)
                //end
            }
        } else {
            //internet off
            if (holder is AzkarParentViewHolder) {
                var viewHolder: AzkarParentViewHolder = holder

                val parentItem = azkarParentList[position]
                viewHolder.iconAzkar.setImageResource(parentItem.icBackground)
                viewHolder.titleAzkar.text = parentItem.title
                viewHolder.childRecyclerView.layoutManager =
                    LinearLayoutManager(viewHolder.itemView.context)
                val childAdapter =
                    AzkarChildAdapter(azkarParentList[position].listAzkar, onChildItemClickListener)
                viewHolder.childRecyclerView.adapter = childAdapter
                viewHolder.titleItem.setOnClickListener {
                    seeSub.invoke(azkarParentList[position])
                }
                viewHolder.childRecyclerView.isVisible = false
                val rotationAngle = if (azkarParentList[position].isChildVisible) 90f else 0f
                val rotationAnimator =
                    ObjectAnimator.ofFloat(viewHolder.iconArrow, "rotation", rotationAngle)
                rotationAnimator.duration = 0L
                rotationAnimator.start()

            }
        }
    }

    private fun loadAdsNativeAzkar(position: Int, viewHolder: AdsViewHolder) {
        Log.d("ntt", "loadAdsNativeAzkar: ")
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
//                                        AdsInter.nativeAdsData1 = nativeAd
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
//                                        AdsInter.nativeAdsData2 = nativeAd
                                    }

//                                    override fun onAdImpression() {
//                                        super.onAdImpression()
//                                        AdsInter.nativeAdsData2=null
//                                    }

                                    override fun onAdFailedToLoad() {
                                        viewHolder.frAds.removeAllViews()
                                    }
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

//            12 -> {
//                try {
//                    if (ConsentHelper.getInstance(context)
//                            .canRequestAds() && AdsInter.isLoadNativeData2
//                    ) {
//
//                        if (AdsInter.nativeAdsData2 != null) {
//                            AdsInter.nativeAdsData2?.let {
//                                pushAdsToView(it, viewHolder.frAds)
//                            }
//
//                        } else {
//                            Admob.getInstance().loadNativeAd(
//                                context,
//                                context.getString(R.string.native_data2),
//                                object : NativeCallback() {
//                                    override fun onNativeAdLoaded(nativeAd: NativeAd) {
//                                        pushAdsToView(nativeAd, viewHolder.frAds)
//                                        AdsInter.nativeAdsData2 = nativeAd
//                                    }
//
//                                    override fun onAdClick() {
//                                        AdsInter.nativeAdsData2 = null
//                                    }
//
//                                    override fun onAdFailedToLoad() {
//                                        viewHolder.frAds.removeAllViews()
//                                    }
//                                }
//                            )
//                        }
//                    } else {
//                        viewHolder.frAds.removeAllViews()
//                        viewHolder.frAds.visibility = View.GONE
//                    }
//                } catch (e: Exception) {
//                    viewHolder.frAds.removeAllViews()
//                }
//            }
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