package com.mavericks.daringmates

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateDare : AppCompatActivity() {

    private lateinit var edtDareTitle: EditText
    private lateinit var edtDareDesc: EditText
    private lateinit var btnSendDare: Button
    private lateinit var dare: Dares

    private lateinit var dareslists: ArrayList<DareLists>
    private lateinit var daresRecyclerView: RecyclerView

    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_dare)

        edtDareTitle = findViewById(R.id.edt_dareTitle)
        edtDareDesc = findViewById(R.id.edt_dareDesc)
        btnSendDare = findViewById(R.id.btn_sendDare)

        daresRecyclerView = findViewById(R.id.recycler_darelist)
        daresRecyclerView.setHasFixedSize(true)
        daresRecyclerView.layoutManager = LinearLayoutManager(this)

        val senderId = intent.getStringExtra("senderId")
        val receiverId = intent.getStringExtra("receiverUid")
        val senderRoom = intent.getStringExtra("sendRoom")
        val receiverRoom = intent.getStringExtra("receiverRoom")

        val dareId = senderId + receiverId

        mDbRef = FirebaseDatabase.getInstance().getReference()

        btnSendDare.setOnClickListener {
            val dareTitle = edtDareTitle.text.trim().toString()
            val dareDesc = edtDareDesc.text.trim().toString()
            dare = Dares(
                 dareId,
                 dareTitle,
                 dareDesc,
                 senderId,
                 receiverId
            )
            mDbRef.child("chats").child(senderRoom!!).child("dares").push().
            setValue(dare).addOnSuccessListener {
                mDbRef.child("chats").child(receiverRoom!!).child("dares").push().
                setValue(dare)
            }
            edtDareTitle.setText("")
            edtDareDesc.setText("")

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        dareslists = ArrayList()


        dareslists.add(DareLists("Cheerful Karaoke", "Sing a song loudly in a public place or in front of everyone."))
        dareslists.add(DareLists("Boisterous Dance", "Do your best dance performance in the middle of a crowded area."))
        dareslists.add(DareLists("Kind Encounters", "Go up to a stranger and give them a compliment or high-five."))
        dareslists.add(DareLists("Fashion Reversal", "Wear your clothes inside out for the next hour."))
        dareslists.add(DareLists("Accented Banter", "Attempt to speak in a foreign accent for the next five minutes."))
        dareslists.add(DareLists("Celebrity Mimicry", "Do a funny imitation of someone famous or a mutual friend."))
        dareslists.add(DareLists("Silly Selfie", "Take a selfie with a silly face and post it on your social media."))
        dareslists.add(DareLists("Unicorn Quest", "Go to a nearby store and ask if they sell unicorn food."))
        dareslists.add(DareLists("Whimsical Tattoo", "Let the group give you a temporary funny tattoo with markers."))
        dareslists.add(DareLists("Rhyming Fun", "Make up a short rap or poem about a random object chosen by the group."))



        val adapter = DaresListsAdapter(dareslists) { option ->
            updateEditTexts(option)
        }

        daresRecyclerView.layoutManager = LinearLayoutManager(this)
        daresRecyclerView.adapter = adapter

    }
    private fun updateEditTexts(dare: DareLists) {
        val setDareTitle = dare.title
        val setDareDesc = dare.desc
        edtDareTitle.setText(setDareTitle)
        edtDareDesc.setText(setDareDesc)
    }
}