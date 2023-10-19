package com.example.apnabank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main3.*
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity3 : AppCompatActivity() {

    val db2 by lazy {
        TransactionDatabase.getDatabase(this)
    }

    var list = arrayListOf<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val user1 = intent.getLongExtra("userId",0)

        val adapter = TransactionAdapter(list)
        rvTransactions.layoutManager = LinearLayoutManager(this)
        rvTransactions.adapter = adapter

        val transactionList = db2.transactionDao().getTransactionHistory(user1)
        transactionList.observe(this, Observer {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
        })


        val calendar = Calendar.getInstance()
        val currDate = SimpleDateFormat("EEE, d MMM yyyy").format(calendar.time)
        val currTime = SimpleDateFormat("h:mm a").format(calendar.time)
//        val currDate = updateDate().toString()
//        val currTime = updateTime().toString()



//
    }
}