package cn.hospital.registerplatform.ui.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import cn.hospital.registerplatform.data.dto.ListItem
import cn.hospital.registerplatform.utils.doWithTry

class BasePagingAdapter<T : ListItem, B : ViewDataBinding>(
    @LayoutRes private val itemLayoutId: Int,
    private val viewHolderBind: (binding: B, data: T) -> Unit
) : PagingDataAdapter<T, BaseViewHolder<T, B>>(ListItem.getDiffCallback()) {
    override fun onBindViewHolder(holder: BaseViewHolder<T, B>, position: Int) {
        doWithTry {
            val item = getItem(position)
            item?.let {
                holder.bind(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T, B> {
        return BaseViewHolder.from(viewHolderBind, itemLayoutId, parent)
    }
}

