package com.github.terrakok.modo

import android.os.Parcelable
import androidx.compose.runtime.Composable

interface Screen : Parcelable {

    @Composable
    fun Content()

    val screenKey: ScreenKey

}
//abstract class Screen(
//    val screenKey: ScreenKey,
//) : Parcelable, ScreenLifecycle by ScreenLifecycleImpl(screenKey) {
//
//    @Composable
//    abstract fun Content()
//
//}