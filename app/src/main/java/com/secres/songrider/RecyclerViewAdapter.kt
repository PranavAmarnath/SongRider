package com.secres.songrider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.secres.songrider.databinding.FragmentFirstBinding


class RecyclerViewAdapter(var songs: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val myTextView: TextView
        val myBtnView: Button

        init {
            myTextView = itemView.findViewById(R.id.text_cardview)
            myBtnView = itemView.findViewById(R.id.btn_cardview)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val listItem: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview, parent, false)

        return MyViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.myTextView.text = songs[position]

        /*
        holder.myBtnView.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        })
        */
    }

    override fun getItemCount() = songs.size

}
