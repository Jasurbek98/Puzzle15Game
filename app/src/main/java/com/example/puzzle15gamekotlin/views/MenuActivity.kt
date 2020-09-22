package com.example.puzzle15gamekotlin.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.puzzle15gamekotlin.R
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        play_button.setOnClickListener { startActivity(Intent(this@MenuActivity,MainActivity::class.java)) }
        about_button.setOnClickListener { startActivity(Intent(this@MenuActivity,AboutActivity::class.java)) }
        exit_button.setOnClickListener { finish() }
    }
}
