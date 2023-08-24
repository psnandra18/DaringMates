package com.mavericks.daringmates

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var dareRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var userName: TextView
    private lateinit var sendDare: Button
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var dareAdapter: DareAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var dareList: ArrayList<Dares>
    private lateinit var mDbRef: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().getReference()

        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        userName = findViewById(R.id.txt_userName)
        userName.text = name

        supportActionBar?.hide()

        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        dareRecyclerView = findViewById(R.id.dareRecyclerView)
        messageBox = findViewById(R.id.chatMessage)
        sendButton = findViewById(R.id.sendMessageButton)
        sendDare = findViewById(R.id.sendDare)
        messageList = ArrayList()
        dareList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)
        dareAdapter = DareAdapter(this,dareList)


        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

        dareRecyclerView.layoutManager = LinearLayoutManager(this)
        dareRecyclerView.adapter = dareAdapter



        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        mDbRef.child("chats").child(senderRoom!!).child("dares")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    dareList.clear()
                    for (postSnapshot in snapshot.children){
                        val dare = postSnapshot.getValue(Dares::class.java)
                        dareList.add(dare!!)
                    }
                    dareAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject =  Message(message,senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push().
            setValue(messageObject).addOnSuccessListener {
                mDbRef.child("chats").child(receiverRoom!!).child("messages").push().
                setValue(messageObject)
            }
            messageBox.setText("")
        }

        sendDare.setOnClickListener {
            val intent = Intent(this,CreateDare::class.java)
            intent.putExtra("senderId",senderUid)
            intent.putExtra("receiverUid",receiverUid)
            intent.putExtra("sendRoom",senderRoom)
            intent.putExtra("receiverRoom",receiverRoom)
            startActivity(intent)
        }
    }
}