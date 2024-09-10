package com.example.quickbuy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.quickbuy.ui.MainActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        videoView = findViewById(R.id.videoView)

        // Set up the video
        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.animation_qb)
        videoView.setVideoURI(uri)
        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            mediaPlayer.start()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            initialLogic()
        }, 4700)


    }

    fun initialLogic() {
        val dataStoreManager = DatastoreManager(this)
        lifecycleScope.launch {
            val isLoggedIn = dataStoreManager.getBoolean("IS_LOGGED_IN", false).first()
            if (isLoggedIn == true)
                navigateToHome()
            else
                navigateToLoginActivity()
        }


    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}