package com.qibla.muslimday.app2025.di

import android.content.Context
import androidx.room.Room
import com.qibla.muslimday.app2025.database.Azkar.AzkarDao
import com.qibla.muslimday.app2025.database.Azkar.AzkarDatabase
import com.qibla.muslimday.app2025.database.Duas.DuasDao
import com.qibla.muslimday.app2025.database.Duas.DuasDatabase
import com.qibla.muslimday.app2025.database.Quran.QuranDao
import com.qibla.muslimday.app2025.database.Quran.QuranDatabase
import com.qibla.muslimday.app2025.database.ayah.AudioDao
import com.qibla.muslimday.app2025.database.ayah.TranslationDao
import com.qibla.muslimday.app2025.database.ayah.TransliterationDao
import com.qibla.muslimday.app2025.database.ayah.VerseDao
import com.qibla.muslimday.app2025.database.ayah.VerseDatabase
import com.qibla.muslimday.app2025.database.ayah.WordsDao
import com.qibla.muslimday.app2025.database.timePray.PrayTimeDao
import com.qibla.muslimday.app2025.database.timePray.PrayTimeDatabase
import com.qibla.muslimday.app2025.ui.salah.calender.preferences.PreferencesHelper
import com.qibla.muslimday.app2025.ui.salah.calender.service.CalendarAPIService
import com.qibla.muslimday.app2025.ui.salah.calender.service.CalendarService
import com.qibla.muslimday.app2025.ui.salah.calender.service.OfflineTimingsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): DuasDatabase {
        return Room.databaseBuilder(
            appContext,
            DuasDatabase::class.java,
            "DailyDuas"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideDuasDao(duasDatabase: DuasDatabase): DuasDao {
        return duasDatabase.duasDao
    }

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideAppAzkarDatabase(@ApplicationContext appContext: Context): AzkarDatabase {
        return Room.databaseBuilder(
            appContext,
            AzkarDatabase::class.java,
            "Azkar"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideAzkarrDao(azkarDatabase: AzkarDatabase): AzkarDao {
        return azkarDatabase.azkarDao
    }

    @Provides
    @Singleton
    fun provideAppVerseDatabase(@ApplicationContext appContext: Context): VerseDatabase {
        return Room.databaseBuilder(
            appContext,
            VerseDatabase::class.java,
            "Verse"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideVerseDao(verseDatabase: VerseDatabase): VerseDao {
        return verseDatabase.verseDao
    }

    @Provides
    @Singleton
    fun provideWordsDao(verseDatabase: VerseDatabase): WordsDao {
        return verseDatabase.wordsDao
    }

    @Provides
    @Singleton
    fun provideAudioDao(verseDatabase: VerseDatabase): AudioDao {
        return verseDatabase.audioDao
    }

    @Provides
    @Singleton
    fun provideTranslationDao(verseDatabase: VerseDatabase): TranslationDao {
        return verseDatabase.translationDao
    }

    @Provides
    @Singleton
    fun provideTransliterationDao(verseDatabase: VerseDatabase): TransliterationDao {
        return verseDatabase.transliterationDao
    }

    @Provides
    @Singleton
    fun provideAppQuranDatabase(@ApplicationContext appContext: Context): QuranDatabase {
        return Room.databaseBuilder(
            appContext,
            QuranDatabase::class.java,
            "Quran"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideQuranDao(quranDatabase: QuranDatabase): QuranDao {
        return quranDatabase.quranDao
    }


    @Provides
    @Singleton
    fun provideAppPrayTimeDatabase(@ApplicationContext appContext: Context): PrayTimeDatabase {
        return Room.databaseBuilder(
            appContext,
            PrayTimeDatabase::class.java,
            "PrayTime"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun providePrayTimeDao(prayTimeDatabase: PrayTimeDatabase): PrayTimeDao {
        return prayTimeDatabase.prayTimeDao
    }

    @Provides
    fun provideCalendarService(
        calendarAPIService: CalendarAPIService?,
        offlineTimingsService: OfflineTimingsService?,
        preferencesHelper: PreferencesHelper?,
    ): CalendarService {
        return CalendarService(calendarAPIService, offlineTimingsService, preferencesHelper)
    }
}