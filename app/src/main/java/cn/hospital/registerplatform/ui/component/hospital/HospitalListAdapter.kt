package cn.hospital.registerplatform.ui.component.hospital

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.hospital.registerplatform.data.dto.ListItem
import cn.hospital.registerplatform.utils.doWithTry

class HospitalListAdapter<T : ListItem, B : ViewDataBinding>(
    @LayoutRes private val itemLayoutId: Int,
    private val viewHolderBind: (binding: B, data: T) -> Unit
) : PagingDataAdapter<T, HospitalViewHolder<T, B>>(ListItem.getDiffCallback()) {
    override fun onBindViewHolder(holder: HospitalViewHolder<T, B>, position: Int) {
        doWithTry {
            val item = getItem(position)
            item?.let {
                holder.bind(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder<T, B> {
        return HospitalViewHolder.from(viewHolderBind, itemLayoutId, parent)
    }
}

class HospitalViewHolder<T : ListItem, B : ViewDataBinding>(
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
        fun <T : ListItem, B : ViewDataBinding> from(
            viewHolderBind: (binding: B, data: T) -> Unit,
            @LayoutRes itemLayoutId: Int,
            parent: ViewGroup
        ): HospitalViewHolder<T, B> {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<B>(layoutInflater, itemLayoutId, parent, false)
            return HospitalViewHolder(binding, viewHolderBind)
        }
    }
}
