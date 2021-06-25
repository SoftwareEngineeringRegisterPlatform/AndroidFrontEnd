package cn.hospital.registerplatform.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<T : Any, B : ViewDataBinding>(
    private val binding: B,
    private val viewHolderBind: (binding: B, data: T) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: T) {
        binding.apply {
            viewHolderBind(this, data)
            executePendingBindings()
        }
    }

    companion object {
        fun <T : Any, B : ViewDataBinding> from(
            viewHolderBind: (binding: B, data: T) -> Unit,
            @LayoutRes itemLayoutId: Int,
            parent: ViewGroup
        ): BaseViewHolder<T, B> {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<B>(layoutInflater, itemLayoutId, parent, false)
            return BaseViewHolder(binding, viewHolderBind)
        }
    }
}
