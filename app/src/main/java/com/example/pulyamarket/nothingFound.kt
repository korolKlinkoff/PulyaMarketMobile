package com.example.pulyamarket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class nothingFound : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nothing_found)
        val nothingFoundText = findViewById<TextView>(R.id.nothingFoundText)
        nothingFoundText.text = "По запросу "+intent.getStringExtra("text")+" ничего не найдено"
        val NothingFoundButton = findViewById<Button>(R.id.NotFoundReturn)
        NothingFoundButton.setOnClickListener{startActivity(Intent(this, MainActivity::class.java))}
    }
}