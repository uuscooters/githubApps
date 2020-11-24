 package com.resmana.githubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_user.view.*

 class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

     private val listUser = ArrayList<Users>()

     fun setData(items: ArrayList<Users>){
         listUser.clear()
         listUser.addAll(items)
         notifyDataSetChanged()
     }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: Users) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(img_photo)

                txt_name.text = user.username

                itemView.setOnClickListener {
                    val userIntent = Intent(itemView.context, DetailActivity::class.java)
                    userIntent.putExtra(DetailActivity.EXTRA_USER, user)
                    itemView.context.startActivity(userIntent)
                }
            }
        }
    }
}

