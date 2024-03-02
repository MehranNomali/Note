package com.example.note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.Timer
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timer().schedule(timerTask {
            startActivity(Intent(this@SplashActivity , MainActivity::class.java))
            finish()
        } , 5000)

        setContentView(R.layout.activity_splash)
    }

}