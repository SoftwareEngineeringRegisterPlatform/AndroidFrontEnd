package cn.hospital.registerplatform.ui.component.main

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class HomeCardData(
    @StringRes
    val title: Int,
    @DrawableRes
    val icon: Int,
    val onClickListener: View.OnClickListener
)
