package com.example.spotfinder.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.spotfinder.databinding.CommetRowLayoutBinding
import com.example.spotfinder.models.comments.Comment
import com.example.spotfinder.util.PlacesDiffUtil

class CommentsAdapter: RecyclerView.Adapter<CommentsAdapter.MyViewHolder>() {

    private var commentsList = emptyList<Comment>()

    class MyViewHolder(private val binding: CommetRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Comment, holder: View) {
            Log.d("imgUser", result.user.toString())
                if (result.user.img != null) {
                    val urlImage = ContextCompat.getDrawable(holder.context, result.user.img.toInt())
                    binding.imvUser.background = urlImage
                    binding.tvUserNameComment.text = result.user.name
                    binding.tvUserCommetText.text = result.text
                }
                //binding.imvUser.load(result.img)

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
        holder.bind(currentComment, holder.itemView)
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