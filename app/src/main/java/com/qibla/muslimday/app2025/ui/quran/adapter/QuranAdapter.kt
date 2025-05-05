package com.qibla.muslimday.app2025.ui.quran.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.nlbn.ads.callback.NativeCallback
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.database.Quran.QuranEntity
import com.qibla.muslimday.app2025.databinding.ItemSurahBinding
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.util.AdsInter

class QuranAdapter(
    private val context: Context,
    private val selectList: Int,
    private var listDataQuran: List<QuranEntity>,
    private val iClickItemQuran: IClickItemQuran,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private var listQuranFiltered = ArrayList<QuranEntity>()


    init {
        listQuranFiltered.addAll(listDataQuran)
    }

    inner class QuranViewHolder(var binding: ItemSurahBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: QuranEntity) {
            Log.d("RecyclerView", "Binding item ${data.id} - isBookMark: ${data.isBookMark}")
            binding.tvName.text = data.name
            binding.tvNum.text = data.num.toString()
            binding.tvNameTranslation.text = data.translation

            if (data.isBookMark) {
                binding.imgBookmark.setImageResource(R.drawable.bg_btn_bookmark_seleced)
            } else {
                binding.imgBookmark.setImageResource(R.drawable.bg_btn_bookmark_not_selected)
            }
        }
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
//        return ViewHolder(
//            ItemSurahBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )

        if (context.hasNetworkConnection()) {
            //internet ol
            if (viewType == TYPE_ADS) {
                return AdsViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_layout_ads_native, parent, false)
                )
            } else {
                return QuranViewHolder(
                    ItemSurahBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        } else {
            //internet off
            return QuranViewHolder(
                ItemSurahBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return listQuranFiltered.size
    }

    override fun getItemViewType(position: Int): Int {
        if (listDataQuran[position].type == "Ads") {
            return TYPE_ADS
        } else {
            return TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (context.hasNetworkConnection()) {
            //internet ol
            if (holder is QuranViewHolder) {
                var viewHolder: QuranViewHolder = holder

                val item = listQuranFiltered[position]
                viewHolder.bind(item)

                viewHolder.binding.llText.setOnClickListener {
                    iClickItemQuran.onClickItemQuran(item)
                }


                viewHolder.binding.imgBookmark.setOnClickListener {
//                    item.isBookMark=!item.isBookMark
//                    if(item.isBookMark){
//                        viewHolder.binding.imgBookmark.setImageResource(R.drawable.bg_btn_bookmark_seleced)
//                    }else{
//                        viewHolder.binding.imgBookmark.setImageResource(R.drawable.bg_btn_bookmark_not_selected)
//
//                    }
                    iClickItemQuran.onClickBookMarkItemQuran(item, position)
                }


            } else {
                var viewHolder: AdsViewHolder = holder as AdsViewHolder
                //ads native
                if (selectList == 1) {
                    loadAdsNativeSurah(position, viewHolder)
                } else if (selectList == 2) {
                    loadAdsNativeSurah(position, viewHolder)
                }

                //end
            }
        } else {
            if (holder is QuranViewHolder) {
                var viewHolder: QuranViewHolder = holder

                val item = listQuranFiltered[position]
                viewHolder.bind(item)

                viewHolder.binding.llText.setOnClickListener {
                    iClickItemQuran.onClickItemQuran(item)
                }


                viewHolder.binding.imgBookmark.setOnClickListener {
                    iClickItemQuran.onClickBookMarkItemQuran(item, position)
                }

            }
        }
    }

    private fun loadAdsNativeSurah(position: Int, viewHolder: AdsViewHolder) {
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
//                                        Log.d("onAdsClick"," onAdImpression")
//
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val quranSearch = p0.toString()
                if (quranSearch.isEmpty()) {
                    listQuranFiltered.clear()
                    listQuranFiltered.addAll(listDataQuran)
                } else {
                    listQuranFiltered.clear()
                    val resultList = ArrayList<QuranEntity>()
                    for (data in listDataQuran) {
                        if (data.name.toLowerCase().contains(
                                p0.toString().toLowerCase()
                            ) || data.translation.contains(p0.toString())
                        ) {
                            resultList.add(data)
                        }
                    }
                    listQuranFiltered.addAll(resultList)
                }
                val filterResults = FilterResults()
                filterResults.values = listQuranFiltered
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listQuranFiltered = p1?.values as ArrayList<QuranEntity>
                notifyDataSetChanged()
                onListFiltered.invoke(listQuranFiltered.isEmpty())

            }

        }
    }

    var onListFiltered: (Boolean) -> Unit = {}

}