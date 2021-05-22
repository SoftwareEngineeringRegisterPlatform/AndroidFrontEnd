package cn.hospital.registerplatform.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import cn.hospital.registerplatform.R
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, url: String) {
    view.load(url) {
        crossfade(true)
        placeholder(R.drawable.ic_baseline_person_24)
        transformations(CircleCropTransformation())
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