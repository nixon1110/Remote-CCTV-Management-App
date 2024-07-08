/*import java.util.regex.Pattern.compile*/

/*import com.sun.tools.javac.Main.compile*/

/*import org.gradle.internal.impldep.com.fasterxml.jackson.core.JsonPointer.compile*/

plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.pushnotification"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pushnotification"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.recyclerview)
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("de.hdodenhof:circleimageview:3.0.1")
    implementation ("com.github.fornewid:neumorphism:0.1.11")


    /*implementation("com.android.support:multidex:1.0.3")
    implementation("com.google.android.material:material:1.3.0-alpha03")
    *//*implementation("com.shreyaspatil:MaterialDialog:2.1")*//*
    implementation ("com.pranavpandey.android:dynamic-toasts:3.2.0")
    *//*implementation ("com.github.chivorns.androidx:smartmaterialspinner:1.2.1")*//*
    *//*implementation ("com.infideap.drawerbehavior:drawer-behavior:1.0.2")*//*
    implementation ("com.github.PhilJay:MPAndroidChart:v2.0.9")
    *//*implementation ("com.github.DavidProdinger:weekdays-selector:1.1.1")*//*

    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.19")
    implementation ("androidx.coordinatorlayout:coordinatorlayout:1.1.0")
    *//*implementation ("me.itangqi.waveloadingview:library:0.3.5")
    implementation ("com.gauravk.bubblenavigation:bubblenavigation:1.0.7")*//*
    implementation ("androidx.constraintlayout:constraintlayout:2.0.2")
    implementation ("com.airbnb.android:lottie:3.4.4")
    implementation ("com.intuit.ssp:ssp-android:1.0.6")
    implementation ("com.intuit.sdp:sdp-android:1.0.5")
    implementation ("com.ogaclejapan.smarttablayout:library:1.2.1@aar")
    *//*implementation ("com.zekapp.library:progreswheelview:1.1.5")*//*
    implementation ("com.google.cloud:google-cloud-dialogflow:2.3.0")
    implementation ("io.grpc:grpc-okhttp:1.32.2")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.github.TutorialsAndroid:KAlertDialog:v14.0.19")
    *//*implementation ("com.chaos.view:pinview:1.4.4")*//*
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")

    *//*implementation ("com.amulyakhare:com.amulyakhare.textdrawable:1.0.1")*/






    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}