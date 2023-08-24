package com.mavericks.daringmates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DaresListsAdapter(private val dareLists: ArrayList<DareLists>,private val onItemClick: (DareLists) -> Unit)
    :RecyclerView.Adapter<DaresListsAdapter.DaresViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaresViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.dare_list,parent,false )
        return  DaresViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dareLists.size
    }

    override fun onBindViewHolder(holder: DaresViewHolder, position: Int) {
        val dares = dareLists[position]
        holder.dareListsTitle.text = dares.title
        holder.dareListsDesc.text = dares.desc
        holder.itemView.setOnClickListener { onItemClick(dares) }
    }

    class DaresViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val dareListsTitle = itemView.findViewById<TextView>(R.id.txt_darelist_title)
        val dareListsDesc = itemView.findViewById<TextView>(R.id.txt_darelist_desc)

        fun bind(dare: DareLists) {
            dareListsTitle.text = dare.title
            dareListsDesc.text = dare.desc
        }
    }
}