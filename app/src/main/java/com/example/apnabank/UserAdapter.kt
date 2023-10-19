package com.example.apnabank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_item.view.*

class UserAdapter(var list: List<User>, private val itemClickListener: OnItemClickListener):RecyclerView.Adapter<UserAdapter.userViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(user: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
        return userViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false))
    }

    fun updateData(users: List<User>) {
        list = users
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        val user = list[position]
        holder.bind(user)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(user)
        }
    }

    override fun getItemCount(): Int = list.size

    class userViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        fun bind(user: User){
            with(itemView){
                tvName.text = user.name
                tvID.text = user.id.toString()
                tvBalance.text = "₹ ${user.balance}"
                imgProfile.setImageResource(user.img)
            }
        }
    }
}
