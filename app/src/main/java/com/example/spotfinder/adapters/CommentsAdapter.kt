package com.example.spotfinder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.spotfinder.databinding.CommetRowLayoutBinding
import com.example.spotfinder.models.comments.Comment
import com.example.spotfinder.util.PlacesDiffUtil

class CommentsAdapter: RecyclerView.Adapter<CommentsAdapter.MyViewHolder>() {

    private var commentsList = emptyList<Comment>()

    class MyViewHolder(private val binding: CommetRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Comment) {
                //binding.imvUser.load(result.img)
                binding.tvUserNameComment.text = result.user.name
                binding.tvUserCommetText.text = result.text

        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CommetRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentComment = commentsList[position]
        holder.bind(currentComment)
    }

    override fun getItemCount(): Int {
       return commentsList.size
    }

    fun setData(newComments: List<Comment>) {
        val commentsDiffUtil = PlacesDiffUtil(commentsList, newComments )
        val diffUtilResult = DiffUtil.calculateDiff(commentsDiffUtil)
        commentsList = newComments
        diffUtilResult.dispatchUpdatesTo(this)

    }
}