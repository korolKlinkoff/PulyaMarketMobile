package com.example.pulyamarket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class basket : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)
        findViewById<Button>(R.id.basketBackButton).setOnClickListener{basketBack()}
    }
    private fun basketBack(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}