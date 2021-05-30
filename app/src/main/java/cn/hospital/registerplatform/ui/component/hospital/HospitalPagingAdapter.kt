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

class HospitalPagingAdapter<T : ListItem, B : ViewDataBinding>(
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

class HospitalListAdapter<T : Any, B : ViewDataBinding>(
    private var itemList: List<T>,
    @LayoutRes private val itemLayoutId: Int,
    private val viewHolderBind: (binding: B, data: T) -> Unit
) : RecyclerView.Adapter<HospitalViewHolder<T, B>>() {
    override fun onBindViewHolder(holder: HospitalViewHolder<T, B>, position: Int) {
        val item = itemList[position]
        item.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder<T, B> {
        return HospitalViewHolder.from(viewHolderBind, itemLayoutId, parent)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateList(itemList: List<T>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }
}

class HospitalViewHolder<T : Any, B : ViewDataBinding>(
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
        ): HospitalViewHolder<T, B> {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<B>(layoutInflater, itemLayoutId, parent, false)
            return HospitalViewHolder(binding, viewHolderBind)
        }
    }
}
