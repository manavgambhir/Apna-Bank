package com.example.apnabank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity2 : AppCompatActivity(), UserAdapter.OnItemClickListener {

    val db by lazy {
        AppDatabase.getDatabase(this)
    }

    private lateinit var userLiveData: LiveData<List<User>>

    var userList:List<User> = emptyList()

    private var isInitialDataInserted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val adapter = UserAdapter(userList,this)

        rvList.layoutManager = LinearLayoutManager(this@MainActivity2)
        rvList.adapter = adapter

        userLiveData = db.userDao().getAllUsersLiveData()
        userLiveData.observe(this,Observer{
            userList = it
            adapter.updateData(userList)
        })


        if(!isInitialDataInserted){
            GlobalScope.launch(Dispatchers.IO) {
                val userCount = db.userDao().getUserCount()
                if(userCount==0){
                    val initialListOfUsers = ListofUsers()
                    db.userDao().insertAll(initialListOfUsers)
                }
                isInitialDataInserted = true
            }
        }
    }

    override fun onItemClick(user: User) {
        Log.d("RecyclerViewItemClick", "Item clicked: ${user.name}")
        val position = userList.indexOf(user)
//        Log.d("RecyclerViewItemClick", "Position: ${position}")
        if (position != -1) {
            val intent = Intent(this, MainActivity4::class.java)
            intent.putExtra("position", position)
            startActivity(intent)
        }
    }

    fun ListofUsers(): List<User> {
        return listOf(
            User("Rohan", "rohan@gmail.com", 10000, "9922446610", R.drawable.man, 102121121),
            User("Arnav", "arnav@gmail.com", 20000, "8844111068", R.drawable.boy, 104121121),
            User("Neha", "neha@gmail.com", 25000, "7562012201", R.drawable.women, 106121121),
            User("Arjun", "arjun@gmail.com", 13000, "8755110132", R.drawable.man,110121121),
            User("Rohit", "rohit@gmail.com", 19000, "9214563100", R.drawable.man, 120121121),
            User("Mohit", "mohit@gmail.com", 12500, "9984892121", R.drawable.boy, 124121121),
            User("Aman", "aman@gmail.com", 22500, "8212345673", R.drawable.man, 128121121),
            User("Rohit", "rohit@gmail.com", 19000, "9893369921", R.drawable.boy, 130121121),
            User("Priya", "priya@gmail.com", 24000, "9891222122", R.drawable.women, 132121121),
            User("Shiv", "shiv@gmail.com", 28000, "8750662198", R.drawable.man, 134121121)
        )
    }
}