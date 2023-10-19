package com.example.apnabank

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    val senderId:Long,
    val senderName: String,
    val receiverId: Long,
    val receiverName: String,
    val amtTransfer:Int,
    val timeStamp: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0L
)
