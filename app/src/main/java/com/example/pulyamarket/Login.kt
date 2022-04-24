package com.example.pulyamarket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.cancelButton).setOnClickListener{cancel()}
        findViewById<Button>(R.id.applyButton).setOnClickListener{apply()}
        findViewById<Button>(R.id.loginByMailButton).setOnClickListener{loginByMail()}
    }
    private fun cancel(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    private fun apply(){
        val phoneNumber = findViewById<EditText>(R.id.PhoneNumber).text
        if(!Regex("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}\$").matches(phoneNumber)){
            Toast.makeText(this, "неправильный номер", Toast.LENGTH_SHORT).show()
            return
        }
        // TODO: когда скажут как, подтверждение номера по коду
    }
    private fun loginByMail(){
        val intent = Intent(this, loginByMail::class.java)
        startActivity(intent)
    }
}