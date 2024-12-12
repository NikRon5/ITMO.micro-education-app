package com.example.microeducation

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_video)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.video)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val videoView = findViewById<VideoView>(R.id.idVideoView)
        val videoUrl = "android.resource://" + packageName + "/" + R.raw.very_fat_cat_sits_on_the_bench
        val uri = Uri.parse(videoUrl)
        videoView.setVideoURI(uri)

        val mediaController = MediaController(this)

        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        mediaController.setMediaPlayer(videoView)
        videoView.setOnPreparedListener { mediaPlayer: MediaPlayer? -> videoView.start() }

    }
}