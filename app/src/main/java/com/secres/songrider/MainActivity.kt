package com.secres.songrider

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.ItemClickListener {

    var adapter: RecyclerViewAdapter? = null
    var songsList: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        songsList = ArrayList()
        songsList?.add("Life is a Highway")
        songsList?.add("Star Wars")
        songsList?.add("Mission Impossible")

        val recyclerView = findViewById<View>(R.id.rvSongs) as RecyclerView
        recyclerView.setHasFixedSize(true)
        adapter = RecyclerViewAdapter(songsList!!)
        adapter?.setClickListener(this)
        recyclerView.adapter = adapter

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = llm
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, llm.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun onItemClick(view: View?, position: Int) {
        if(position == 0) {
            val intent = Intent(this, SongActivity::class.java).apply {
                putExtra("com.secres.songrider.MESSAGE", songsList!![position])
            }
            startActivity(intent)
        }
    }

}