# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\hasee\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontwarn com.flurry.sdk.*
-dontwarn io.fabric.sdk.android.services.common.*

-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**
-keep class io.fabric.sdk.** { *; }
-keep class io.fabric.sdk.**


-keepclasseswithmembers class * {            # 保持自定义控件类不被混淆
    public <init>(android.content.Context);
}

-keepclasseswithmembers class * {            # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {            # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#greendao
-keep public class com.pheth.hasee.stickerhero.dao.** {
     public static <fields>;
}
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties



#IMOJI SDK
-dontwarn com.fotoable.keyboard.emoji.imoji.fragment.*
-dontwarn com.fotoable.keyboard.emoji.theme.apk.ThemeResourceManager
-dontwarn com.fotoable.keyboard.emoji.ui.emoji.PagerSlidingTabStrip
-dontwarn com.fotoable.keyboard.emoji.ui.emoji.**
-dontwarn com.shizhefei.view.indicator.slidebar.DrawableBar
-dontwarn io.imoji.sdk.editor.**

-dontwarn com.facebook.imageutils.BitmapUtil
-dontwarn com.facebook.imagepipeline.nativecode.Bitmaps
-dontwarn com.facebook.imagepipeline.memory.BitmapPool
-dontwarn com.facebook.imagepipeline.animated.util.AnimatedDrawableUtil
-dontwarn com.facebook.drawee.view.DraweeView

####################################################################
### from fresco
# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Facebook Fresco
# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
# Works around a bug in the animated GIF module which will be fixed in 0.12.0
-keep class com.facebook.imagepipeline.animated.factory.AnimatedFactoryImpl {
    public AnimatedFactoryImpl(com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory,com.facebook.imagepipeline.core.ExecutorSupplier);
}

#okhttp3
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *; }
-keep interface com.squareup.okhttp3.** { *; }

-assumenosideeffects class android.util.Log {

  public static boolean isLoggable(java.lang.String,int);

  public static int v(...);

  public static int i(...);

  public static int w(...);

  public static int d(...);

 public static int e(...);

}
