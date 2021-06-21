package cn.hospital.registerplatform.utils

import android.text.format.DateFormat
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import cn.hospital.registerplatform.R
import coil.load
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, url: String?) {
    url?.let {
        view.load(it) {
            crossfade(true)
            placeholder(R.drawable.ic_baseline_person_24)
        }
    }
}

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, @DrawableRes res: Int) {
    view.load(res) {
        crossfade(true)
    }
}

@BindingAdapter("loadStringChips")
fun loadStringChips(view: ChipGroup, chips: List<String>) {
    view.removeAllViews()
    chips.forEach {
        view.addView(
            Chip(view.context).apply {
                text = it
                isCheckable = false
                isClickable = false
            }
        )
    }
}

@BindingAdapter("loadText")
fun loadText(view: TextView, @StringRes res: Int) {
    view.text = view.context.getString(res)
}

const val ZH_WEEKDAY = "一二三四五六日"

@BindingAdapter("loadText")
fun loadText(view: TextView, date: Date) {
    view.text = DateFormat.format("MM-dd", date).toString() + " 周" + ZH_WEEKDAY[date.day]
}
