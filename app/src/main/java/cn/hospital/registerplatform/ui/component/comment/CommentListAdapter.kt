package cn.hospital.registerplatform.ui.component.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.hospital.registerplatform.data.dto.SingleComment
import cn.hospital.registerplatform.databinding.CommentListItemBinding
import cn.hospital.registerplatform.utils.doWithTry

class CommentListAdapter :
    PagingDataAdapter<SingleComment, CommentListItemHolder>(SingleComment.DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListItemHolder {
        return CommentListItemHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CommentListItemHolder, position: Int) {
        doWithTry {
            val item = getItem(position)
            item?.let {
                holder.bind(it)
            }
        }
    }
}

class CommentListItemHolder(
    private val binding: CommentListItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: SingleComment) {
        binding.apply {
            item = data
            executePendingBindings()
        }
    }

    companion object {
        fun from(
            parent: ViewGroup
        ): CommentListItemHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CommentListItemBinding.inflate(layoutInflater, parent, false)
            return CommentListItemHolder(binding)
        }
    }
}