package com.example.microeducation.ui.course

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.microeducation.R

class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        val frameLayout = FrameLayout(this).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        }

        // From net
        val videoUrl = "https://static.videezy.com/system/resources/previews/000/052/598/original/56.mp4"

        // Local
        //val videoUrl = "android.resource://" + packageName + "/" + R.raw.very_fat_cat_sits_on_the_bench

        val uri = Uri.parse(videoUrl)

        val videoView = VideoView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                MATCH_PARENT,
                MATCH_PARENT
            ).also { params ->
                params.width = resources.displayMetrics.widthPixels
                params.height = (params.width * 9 / 16)
                params.gravity = Gravity.CENTER
            }
            setVideoURI(uri)
            start()
        }

        val mediaController = MediaController(this)
        videoView.setMediaController(mediaController);

        mediaController.setAnchorView(videoView);
        mediaController.setMediaPlayer(videoView)

        frameLayout.setBackgroundColor(getColor(R.color.black))
        frameLayout.addView(videoView)
        setContentView(frameLayout)

        mediaController.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            @RequiresApi(Build.VERSION_CODES.R)
            override fun onGlobalLayout() {
                mediaController.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val display = windowManager.currentWindowMetrics.bounds
                val screenWidth = display.width()

                val newWidth = (screenWidth * 0.9f).toInt()

                mediaController.layoutParams.width = newWidth
                mediaController.requestLayout()

                val paddingLeft = (screenWidth - newWidth) / 2
                mediaController.setPadding(paddingLeft, 0, 0, 0)
            }
        })
    }
}