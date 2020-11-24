package com.resmana.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_user.view.*
import com.resmana.githubuser.FollowingAdapter.FollowingViewHolder as FollowingViewHolder1

class FollowingAdapter : RecyclerView.Adapter<FollowingViewHolder1>() {

    private val listFollowing = ArrayList<Users>()

    fun setData(items: ArrayList<Users>){
        listFollowing.clear()
        listFollowing.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): FollowingViewHolder1 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return FollowingViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder1, i: Int) {
        holder.bind(listFollowing[i])

    }

    override fun getItemCount(): Int = listFollowing.size

    class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataFollowing: Users){
            with(itemView){
                Glide.with(itemView.context)
                    .load(dataFollowing.avatar)
                    .apply(RequestOptions().override(20, 20))
                    .into(img_photo)

                txt_name.text = dataFollowing.username
            }
        }

    }


}