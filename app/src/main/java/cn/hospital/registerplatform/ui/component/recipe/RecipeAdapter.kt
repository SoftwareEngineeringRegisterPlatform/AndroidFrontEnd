package cn.hospital.registerplatform.ui.component.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class RecipeListAdapter<T : Any, B : ViewDataBinding>(
    @LayoutRes private val itemLayoutId: Int,
    private val viewHolderBind: (binding: B, data: T) -> Unit
) : RecyclerView.Adapter<RecipeViewHolder<T, B>>() {
    private var itemList: List<T> = listOf()
    override fun onBindViewHolder(holder: RecipeViewHolder<T, B>, position: Int) {
        val item = itemList[position]
        item.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder<T, B> {
        return RecipeViewHolder.from(viewHolderBind, itemLayoutId, parent)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateList(itemList: List<T>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }
}

class RecipeViewHolder<T : Any, B : ViewDataBinding>(
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
        ): RecipeViewHolder<T, B> {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<B>(layoutInflater, itemLayoutId, parent, false)
            return RecipeViewHolder(binding, viewHolderBind)
        }
    }
}
