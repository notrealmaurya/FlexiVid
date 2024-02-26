package com.maurya.flexivid.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.maurya.flexivid.MainActivity
import com.maurya.flexivid.dataEntities.FolderDataClass
import com.maurya.flexivid.dataEntities.VideoDataClass
import com.maurya.flexivid.databinding.ActivitySplashBinding
import com.maurya.flexivid.util.getAllVideos
import kotlinx.coroutines.launch


class SplashActivity : AppCompatActivity() {

    private lateinit var activitySplashBinding: ActivitySplashBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(activitySplashBinding.root)

        Handler(Looper.myLooper()!!).postDelayed(
            {
                val intent = Intent(
                    this@SplashActivity,
                    MainActivity::class.java
                )
                startActivity(intent)
                finish()
            },
//            2250
            2000
        )


    }
}