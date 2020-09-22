package com.example.puzzle15gamekotlin.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.puzzle15gamekotlin.R

class SplashActivity : AppCompatActivity() {
    lateinit var countDownTimer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        countDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                finish()
                startActivity(Intent(this@SplashActivity, MenuActivity::class.java))
            }

        }.start()

    }
}
