package com.example.apnabank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main4.*
import kotlinx.android.synthetic.main.transfer_diallog.*
import kotlinx.android.synthetic.main.user_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity4 : AppCompatActivity() {
    lateinit var myList: List<String>

    var userListLiveData = MutableLiveData<List<User>?>()

    val db by lazy{
        AppDatabase.getDatabase(this);
    }

    val db2 by lazy {
        TransactionDatabase.getDatabase(this)
    }

    var userId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val position = intent.getIntExtra("position",-1)

        db.userDao().getAllUsersLiveData().observe(this, Observer {
            if (position != -1 && position < it.size) {
                val user = it[position]

                userName.text = user.name
                userId = user.id
                accNo.text = userId.toString()
                num.text = user.phone
                mail.text = user.email
                bal.text = "₹${user.balance}"
                imageView2.setImageResource(user.img)
            }
        })

        GlobalScope.launch(Dispatchers.IO){
            myList = db.userDao().getAllUserNames()
        }

        userListLiveData = MutableLiveData()

        db.userDao().getAllUsersLiveData().observe(this, Observer { userList ->
            userListLiveData.postValue(userList)
        })

        btnTransfer.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.transfer_diallog, null)
            val transferBox = AlertDialog.Builder(this)
                .setTitle("Transfer To:")
                .setView(dialogView)
                .create()

            val spinner = dialogView.findViewById<Spinner>(R.id.spinnerUsers)
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, myList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            val transAmt = dialogView.findViewById<EditText>(R.id.etAmount)
            val transferButton = dialogView.findViewById<Button>(R.id.btnTransact)
            val cancelBtn = dialogView.findViewById<Button>(R.id.btnCancel)

            cancelBtn.setOnClickListener {
                transferBox.dismiss()
//                Toast.makeText(this@MainActivity4,"Cancel Clicked",Toast.LENGTH_SHORT).show()
                val failDialog = LayoutInflater.from(this).inflate(R.layout.transaction_fail,null)
                val failBox = AlertDialog.Builder(this@MainActivity4)
                    .setView(failDialog)
                    .create()
                failBox.show()

                val closeBtn = failDialog.findViewById<Button>(R.id.btnClose)
                closeBtn.setOnClickListener {
                    failBox.dismiss()
                }
            }

            transferButton.setOnClickListener {
                val selectedUserPosition = spinner.selectedItemPosition
                val transferAmt = transAmt.text.toString().toInt()
                if(transferAmt>0){
                    val userList = userListLiveData.value
                    if(userList != null && selectedUserPosition < userList.size){
                        val selectedUser = userList[selectedUserPosition]
                        val transferring = userList[position]
                        if(selectedUser.balance >= transferAmt) {
                            transferring.balance -= transferAmt
                            selectedUser.balance += transferAmt

                            val transaction = Transaction(
                                senderId = transferring.id,
                                senderName = transferring.name,
                                receiverId = selectedUser.id,
                                receiverName = selectedUser.name,
                                amtTransfer = transferAmt
                            )

                            GlobalScope.launch(Dispatchers.IO) {
                                db.userDao().updateUser(transferring)
                                db.userDao().updateUser(selectedUser)
                                db2.transactionDao().insertTransaction(transaction)
                            }
                            userListLiveData.postValue(userList)
                        }
                    }
                }
                transferBox.dismiss()
                val successDialog = LayoutInflater.from(this).inflate(R.layout.transaction_success,null)
                val successBox = AlertDialog.Builder(this@MainActivity4)
                    .setView(successDialog)
                    .create()
                successBox.show()

                val closeBtn = successDialog.findViewById<Button>(R.id.closeBtn)
                closeBtn.setOnClickListener {
                    successBox.dismiss()
                }
            }
            transferBox.show()
        }


        btnTrans.setOnClickListener {
            val i = Intent(this@MainActivity4, MainActivity3::class.java)
            val USERid = userId
            i.putExtra("userId",USERid)
            startActivity(i)
        }
    }
}