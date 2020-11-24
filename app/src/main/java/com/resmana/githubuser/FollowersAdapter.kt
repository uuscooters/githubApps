package com.resmana.githubuser


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_user.view.*

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    private val listFollowers = ArrayList<Users>()

    fun setData(items: ArrayList<Users>){
        listFollowers.clear()
        listFollowers.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): FollowersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return FollowersViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, i: Int) {
       holder.bind(listFollowers[i])
    }

    override fun getItemCount(): Int = listFollowers.size

    class FollowersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(dataFollower: Users){
            with(itemView){
                Glide.with(itemView.context)
                    .load(dataFollower.avatar)
                    .apply(RequestOptions().override(20, 20))
                    .into(img_photo)

                txt_name.text = dataFollower.username
            }
        }
    }

}