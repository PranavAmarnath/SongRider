package com.secres.songrider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(var songs: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    var mClickListener: ItemClickListener? = null

    // stores and recycles views as they are scrolled off screen
    inner class MyViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var myTextView: TextView

        init {
            myTextView = itemView.findViewById(R.id.songName)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            mClickListener?.onItemClick(view, adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val listItem: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_row, parent, false)

        return MyViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.myTextView.text = songs[position]
    }

    // total number of rows
    override fun getItemCount() = songs.size

    // convenience method for getting data at click position
    fun getItem(id: Int): String {
        return songs[id]
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

}
