package com.example.apnabank

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE senderId = :userId OR receiverId = :userId ORDER BY timestamp DESC")
    fun getTransactionHistory(userId:Long): LiveData<List<Transaction>>

//    @Update
//    suspend fun updateTransaction(transaction: Transaction)
}
