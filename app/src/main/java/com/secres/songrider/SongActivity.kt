package com.secres.songrider

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SongActivity : AppCompatActivity() {

    private var forwardButton: Button? = null
    private var pauseButton: Button? = null
    private var rewindButton: Button? = null
    private var playButton: Button? = null
    private var mediaPlayer: MediaPlayer? = null

    private var startTime = 0.0
    private var finalTime = 0.0

    private val myHandler: Handler = Handler()
    private val forwardTime = 5000
    private val backwardTime = 5000
    private var seekbar: SeekBar? = null
    private var tx1: TextView? = null
    private var tx2:TextView? = null
    private var tx3:TextView? = null

    private var oneTimeOnly = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_song)

        // Get the Intent that started this activity and extract the string
        val message = intent.getStringExtra("com.secres.songrider.MESSAGE")

        forwardButton = findViewById(R.id.forwardButton)
        pauseButton = findViewById(R.id.pauseButton)
        rewindButton = findViewById(R.id.rewindButton)
        playButton = findViewById(R.id.playButton)

        tx1 = findViewById(R.id.textView2)
        tx2 = findViewById(R.id.textView3)
        tx3 = findViewById(R.id.textView4)
        tx3?.text = message

        val url = "http://www.moviewavs.com/0053148414/MP3S/Movies/Star_Wars/starwars.mp3" // your URL here
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(url)
            prepareAsync() // might take long! (for buffering, etc)
        }
        val mOnPreparedListener = OnPreparedListener { mediaPlayer ->
            mediaPlayer?.start()
        }
        mediaPlayer?.setOnPreparedListener(mOnPreparedListener)
        seekbar = findViewById(R.id.seekBar)
        seekbar?.isClickable = false
        pauseButton?.isEnabled = false

        rewindButton?.setOnClickListener {
            finalTime = mediaPlayer!!.duration.toDouble()
            startTime = mediaPlayer!!.currentPosition.toDouble()

            if (oneTimeOnly == 0) {
                seekbar?.setMax(finalTime.toInt())
                oneTimeOnly = 1
            }
        }
    }
}