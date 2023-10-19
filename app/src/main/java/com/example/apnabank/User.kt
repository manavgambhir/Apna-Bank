package com.example.apnabank

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class User(
    val name:String,
    val email:String,
    var balance:Int,
    val phone:String,
    val img:Int,
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0
)