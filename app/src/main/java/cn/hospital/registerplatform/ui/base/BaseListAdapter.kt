package cn.hospital.registerplatform.ui.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseListAdapter<T : Any, B : ViewDataBinding>(
    private var itemList: List<T>,
    @LayoutRes private val itemLayoutId: Int,
    private val viewHolderBind: (binding: B, data: T) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<T, B>>() {
    override fun onBindViewHolder(holder: BaseViewHolder<T, B>, position: Int) {
        val item = itemList[position]
        item.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T, B> {
        return BaseViewHolder.from(viewHolderBind, itemLayoutId, parent)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateList(itemList: List<T>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }
}
