package com.example.pulyamarket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class loginByMail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_by_mail)
        findViewById<Button>(R.id.cancelEmailLoginButton).setOnClickListener{cancelEmailLogin()}
        findViewById<Button>(R.id.applyEmailLoginButton).setOnClickListener{applyEmailLogin()}
    }
    private fun cancelEmailLogin(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    private fun applyEmailLogin(){
        val emailInput = findViewById<EditText>(R.id.emailAdress).text.toString()
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            Toast.makeText(this, "неправильный email", Toast.LENGTH_SHORT).show()
            return
        }
        // TODO: валидатор пароля
        // TODO: запрос на подтверждение
    }
}