package com.example.puzzle15gamekotlin.views

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.puzzle15gamekotlin.R

class RecordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        val recordScore = findViewById<TextView>(R.id.recordScore)
        val recordTime = findViewById<TextView>(R.id.recordTime)
        val bundle = intent.extras
        findViewById<View>(R.id.back_record_button).setOnClickListener {
            setResult(1)
            finish()
        }
        findViewById<View>(R.id.restart_record_button).setOnClickListener {
            setResult(2)
            finish() }

        if (bundle != null) {
            recordScore.text = bundle.getInt("SCORE", 0).toString()
            recordTime.text = bundle.getString("TIME", "--:--")
        }

    }
    }
