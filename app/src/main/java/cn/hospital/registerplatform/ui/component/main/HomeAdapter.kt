package cn.hospital.registerplatform.ui.component.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.hospital.registerplatform.databinding.ItemMainButtonBinding

class HomeAdapter(private val buttonList: List<HomeCardData>) :
    RecyclerView.Adapter<HomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            ItemMainButtonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val button = buttonList[position]
        holder.bindData(button)
    }

    override fun getItemCount(): Int {
        return buttonList.size
    }
}

class HomeViewHolder(private val binding: ItemMainButtonBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindData(data: HomeCardData) {
        binding.apply {
            item = data
            executePendingBindings()
        }
    }
}
