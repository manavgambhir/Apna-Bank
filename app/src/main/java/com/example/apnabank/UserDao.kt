package com.example.apnabank

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Insert
    suspend fun insertAll(list: List<User>)

    @Query("SELECT * FROM User")
    fun getAllUsersLiveData(): LiveData<List<User>>

    @Query("SELECT * FROM User")
    fun getAllUsers(): List<User>

    @Query("SELECT name FROM User")
    fun getAllUserNames(): List<String>

    @Query("SELECT COUNT(*) FROM User")
    suspend fun getUserCount(): Int

    @Update
    suspend fun updateUser(user: User)
}