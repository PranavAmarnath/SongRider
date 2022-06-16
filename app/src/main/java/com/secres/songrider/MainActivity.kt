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

    private var adapter: RecyclerViewAdapter? = null
    private var songs: LinkedHashMap<String, String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        songs = LinkedHashMap()
        songs?.put("Top Gun", "http://www.moviewavs.com/0053148414/MP3S/Movies/Top_Gun/youvlost.mp3")
        songs?.put("Star Wars", "http://www.moviewavs.com/0053148414/MP3S/Movies/Star_Wars/starwars.mp3")
        songs?.put("Life is a Highway", "http://www.moviewavs.com/0053148414/MP3S/Movies/Cars/lifeisahighway.mp3")

        val recyclerView = findViewById<View>(R.id.rvSongs) as RecyclerView
        recyclerView.setHasFixedSize(true)
        adapter = RecyclerViewAdapter(songs!!)
        adapter?.setClickListener(this)
        recyclerView.adapter = adapter

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = llm
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, llm.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun onItemClick(view: View?, position: Int) {
        val intent = Intent(this, SongActivity::class.java).apply {
            putExtra("com.secres.songrider.MESSAGE_1", songs?.keys?.toList()?.get(position))
            putExtra("com.secres.songrider.MESSAGE_2", songs?.get(songs?.keys?.toList()?.get(position)))
        }
        startActivity(intent)
    }

}