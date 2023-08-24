package com.mavericks.daringmates

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class DareAdapter (val context: Context, val dareList: ArrayList<Dares>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val dareReceive = 1
    val dareSent = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            val view: View = LayoutInflater.from(context).inflate(R.layout.receive_dare,parent,false )
            return ReceiveDareViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent_dare,parent,false )
            return SentDareViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return dareList.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentDare = dareList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentDare.dareSendID )){
            return dareSent
        } else {
            return dareReceive
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentDare = dareList[position]

        if (holder.javaClass == SentDareViewHolder::class.java){
            val viewHolder = holder as SentDareViewHolder
            holder.sentDareTitle.text = currentDare.dareTitle
            holder.sentDareDesc.text = currentDare.dareDesc
        }else {
            val viewHolder = holder as ReceiveDareViewHolder
            holder.receiveDareTitle.text = currentDare.dareTitle
            holder.receiveDareDesc.text = currentDare.dareDesc
        }
    }

    class ReceiveDareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receiveDareTitle = itemView.findViewById<TextView>(R.id.txt_receive_dare_title)
        val receiveDareDesc = itemView.findViewById<TextView>(R.id.txt_receive_dare_desc)
    }

    class SentDareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sentDareTitle = itemView.findViewById<TextView>(R.id.txt_sent_dare_title)
        val sentDareDesc = itemView.findViewById<TextView>(R.id.txt_sent_dare_desc)
    }

}