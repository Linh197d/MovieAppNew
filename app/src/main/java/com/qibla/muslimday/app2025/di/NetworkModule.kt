package com.qibla.muslimday.app2025.di

import android.util.Log
import com.qibla.muslimday.app2025.network.MosqueService
import com.qibla.muslimday.app2025.network.NominatimService
import com.qibla.muslimday.app2025.network.PrayTimeNewService
import com.qibla.muslimday.app2025.network.PrayTimeService
import com.qibla.muslimday.app2025.network.QuranService
import com.qibla.muslimday.app2025.network.WikimediaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val userAgentInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", "linh197d@gmail.com)") // ⚠️ Đổi email của bạn
                .build()
            chain.proceed(request)
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
//            .addInterceptor(userAgentInterceptor)
            .addInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)

                if (!response.isSuccessful) {
                    // Xử lý lỗi ở đây
                    Log.e("API Error", "If API request failed with code: ${response.code}")
                } else {
                    Log.e("API Error", "If API request true with code: ${response.code}")
                }

                response
            }
            .build()
    }

    @Singleton
    @Provides
    @Named("retrofit_time_pray")
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("http://www.islamicfinder.us/index.php/api/")
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(@Named("retrofit_time_pray") retrofit: Retrofit): PrayTimeService =
        retrofit.create(PrayTimeService::class.java)

    @Singleton
    @Provides
    @Named("retrofit_time_pray_new")
    fun provideRetrofitPrayerTime(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://api.aladhan.com/v1/timings/")
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiServicePrayerTime(@Named("retrofit_time_pray_new") retrofit: Retrofit): PrayTimeNewService =
        retrofit.create(PrayTimeNewService::class.java)

    @Singleton
    @Provides
    @Named("retrofit_quran")
    fun provideRetrofitQuran(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://api.quran.com/api/v4/")
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiServiceQuran(@Named("retrofit_quran") retrofit: Retrofit): QuranService =
        retrofit.create(QuranService::class.java)


    @Named("adhan_api")
    @Provides
    @Singleton
    fun provideAdhanApiRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://api.aladhan.com/v1/")
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    @Named("Overpass")
    fun provideOverpassRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://overpass-api.de/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("Nominatim")
    fun provideNominatimRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://nominatim.openstreetmap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("Wiki")
    fun provideWikiRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.wikidata.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOverpassService(@Named("Overpass") retrofit: Retrofit): MosqueService {
        return retrofit.create(MosqueService::class.java)
    }

    @Provides
    @Singleton
    fun provideNominatimService(@Named("Nominatim") retrofit: Retrofit): NominatimService {
        return retrofit.create(NominatimService::class.java)
    }

    @Provides
    @Singleton
    fun provideWikiService(@Named("Wiki") retrofit: Retrofit): WikimediaApiService {
        return retrofit.create(WikimediaApiService::class.java)
    }
}