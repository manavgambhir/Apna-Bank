package com.example.apnabank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.transaction_history_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter(private val listItem:List<Transaction>): RecyclerView.Adapter<TransactionAdapter.TransactionViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewholder {
        return TransactionViewholder(LayoutInflater.from(parent.context).inflate(R.layout.transaction_history_item,parent,false))
    }

    override fun onBindViewHolder(holder: TransactionViewholder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int = listItem.size

    class TransactionViewholder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(transaction: Transaction) {
            with(itemView){
                name1.text = transaction.senderName
                name2.text = transaction.receiverName
                newBal.text = "₹ ${transaction.amtTransfer}"
                tvDate.text = formatMilliSecondsToDate(transaction.timeStamp)
                tvTime.text = formatMilliSecondsToTime(transaction.timeStamp)
            }
        }

    }
}

fun formatMilliSecondsToDate(milliseconds: Long): String {
    val sdf = SimpleDateFormat("dd MMM, yyyy")
    val resultDate = Date(milliseconds)
    return sdf.format(resultDate)
}

fun formatMilliSecondsToTime(milliseconds: Long): String {
    val sdf = SimpleDateFormat("hh:mm a")
    val resultDate = Date(milliseconds)
    return sdf.format(resultDate)
}