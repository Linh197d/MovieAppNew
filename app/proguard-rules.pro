# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keepnames @dagger.hilt.android.lifecycle.HiltViewModel class * extends androidx.lifecycle.ViewModel
#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}
-keep class com.qibla.muslimday.app2025.ui.salah.** {public *;}
-keep class com.qibla.muslimday.app2025.ui.salah.calender** {public *;}
-dontwarn android.media.LoudnessCodecController$OnLoudnessCodecUpdateListener
-dontwarn android.media.LoudnessCodecController
# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn com.facebook.infer.annotation.Nullsafe$Mode
-dontwarn com.facebook.infer.annotation.Nullsafe

-keepclassmembers class com.qibla.muslimday.app2025.model.*{
<fields>;
<init>();
<methods>;
}
-keep class com.qibla.muslimday.app2025.model.** { *; }

-keepclassmembers class com.qibla.muslimday.app2025.ui.ramadan.*{
<fields>;
<init>();
<methods>;
}
-keep class com.qibla.muslimday.app2025.ui.ramadan.** { *; }

-keepclassmembers class com.qibla.muslimday.app2025.ui.ramadan.modelBackup.*{
<fields>;
<init>();
<methods>;
}
-keep class com.qibla.muslimday.app2025.ui.ramadan.modelBackup.** { *; }
-keep class com.qibla.muslimday.app2025.network.** { *; }
-keepclassmembers class com.qibla.muslimday.app2025.network.*{
<fields>;
<init>();
<methods>;
}

-keepclassmembers class com.qibla.muslimday.app2025.util.*{
<fields>;
<init>();
<methods>;
}
-keep class com.qibla.muslimday.app2025.util.** { *; }
-keep class com.qibla.muslimday.app2025.ui.salah.calender.** { *; }
-keepclassmembers class com.qibla.muslimday.app2025.ui.salah.calender.*{
<fields>;
<init>();
<methods>;
}

-keepclassmembers class com.qibla.muslimday.app2025.database.*{
<fields>;
<init>();
<methods>;
}
-keep class com.qibla.muslimday.app2025.database.** { *; }
-keep class androidx.lifecycle.LiveData { *; }

 # Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
 -keep,allowobfuscation,allowshrinking interface retrofit2.Call
 -keep,allowobfuscation,allowshrinking class retrofit2.Response

 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
 # With R8 full mode generic signatures are stripped for classes that are not
  # kept. Suspend functions are wrapped in continuations where the type argument
  # is used.
  -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

  # R8 full mode strips generic signatures from return types if not kept.
  -if interface * { @retrofit2.http.* public *** *(...); }
  -keep,allowoptimization,allowshrinking,allowobfuscation class <3>

  # With R8 full mode generic signatures are stripped for classes that are not kept.
  -keep,allowobfuscation,allowshrinking class retrofit2.Response

  -dontwarn org.jetbrains.annotations.**
  -keep class kotlin.Metadata { *; }

  -keepclassmembers class * {
      @com.squareup.moshi.FromJson <methods>;
      @com.squareup.moshi.ToJson <methods>;
  }