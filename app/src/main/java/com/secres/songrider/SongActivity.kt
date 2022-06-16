package com.secres.songrider

import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import java.util.concurrent.TimeUnit


class SongActivity : AppCompatActivity() {

    private var rewindButton: Button? = null
    private var pauseButton: Button? = null
    private var playButton: Button? = null
    private var forwardButton: Button? = null
    private var mediaPlayer: MediaPlayer? = null

    private var startTime = 0.0
    private var finalTime = 0.0

    private val myHandler: Handler = Handler()
    private val forwardTime = 5000
    private val backwardTime = 5000
    private var seekBar: SeekBar? = null
    private var beginTime: TextView? = null
    private var endTime: TextView? = null
    private var songNameText: TextView? = null

    private var oneTimeOnly = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_song)

        // Get the Intent that started this activity and extract the string
        val message_1 = intent.getStringExtra("com.secres.songrider.MESSAGE_1")
        val message_2 = intent.getStringExtra("com.secres.songrider.MESSAGE_2")

        rewindButton = findViewById(R.id.rewindButton)
        pauseButton = findViewById(R.id.pauseButton)
        playButton = findViewById(R.id.playButton)
        forwardButton = findViewById(R.id.forwardButton)

        beginTime = findViewById(R.id.beginTime)
        endTime = findViewById(R.id.endTime)
        val linearLayout = findViewById<LinearLayout>(R.id.songNameLayout)
        songNameText = TextView(this@SongActivity)
        songNameText?.text = message_1
        linearLayout.setBackgroundColor(Color.TRANSPARENT)
        linearLayout.addView(songNameText)

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(message_2)
            prepareAsync() // might take long! (for buffering, etc)
        }
        seekBar = findViewById(R.id.seekBar)

        seekBar?.isClickable = false
        pauseButton?.isEnabled = false
        playButton?.isEnabled = false

        val updateSongTime: Runnable = object : Runnable {
            override fun run() {
                startTime = mediaPlayer!!.currentPosition.toDouble()
                beginTime?.text = String.format(
                    "%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()),
                    TimeUnit.MILLISECONDS.toSeconds(startTime.toLong()) -
                        TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(
                                startTime.toLong()
                            )
                        )
                )
                seekBar?.progress = startTime.toInt()
                myHandler.postDelayed(this, 100)
            }
        }

        val mOnPreparedListener = OnPreparedListener { mediaPlayer ->
            finalTime = mediaPlayer.duration.toDouble()
            startTime = mediaPlayer.currentPosition.toDouble()
            playButton?.isEnabled = true
            seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    if(fromUser) {
                        startTime = progress.toDouble()
                        mediaPlayer?.seekTo(startTime.toInt())
                        myHandler.postDelayed(updateSongTime, 100)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) { }

                override fun onStopTrackingTouch(seekBar: SeekBar?) { }
            })
        }
        mediaPlayer?.setOnPreparedListener(mOnPreparedListener)

        playButton?.setOnClickListener {
            if (oneTimeOnly == 0) {
                seekBar?.max = finalTime.toInt()
                oneTimeOnly = 1
            }

            mediaPlayer?.start()

            beginTime?.text = (
                String.format(
                    Locale.US,
                    "%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()),
                    TimeUnit.MILLISECONDS.toSeconds(startTime.toLong()) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()))
                )
            )

            endTime?.text = (
                String.format(
                    Locale.US,
                    "%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong()),
                    TimeUnit.MILLISECONDS.toSeconds(finalTime.toLong()) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong()))
                )
            )

            seekBar?.progress = startTime.toInt()
            myHandler.postDelayed(updateSongTime, 100)
            pauseButton?.isEnabled = true
            playButton?.isEnabled = false
        }

        pauseButton?.setOnClickListener {
            mediaPlayer?.pause()
            pauseButton?.isEnabled = false
            playButton?.isEnabled = true
        }

        forwardButton?.setOnClickListener {
            if((startTime + forwardTime) <= finalTime) {
                startTime += forwardTime
                mediaPlayer?.seekTo(startTime.toInt())
            }
            else {
                mediaPlayer?.seekTo(finalTime.toInt())
            }
        }

        rewindButton?.setOnClickListener {
            if((startTime - backwardTime) > 0) {
                startTime -= backwardTime
                mediaPlayer?.seekTo(startTime.toInt())
            }
            else {
                mediaPlayer?.seekTo(0)
            }
        }
    }

}