package com.example.pulyamarket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.net.URL
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.Identity.encode
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.linksText).movementMethod = LinkMovementMethod.getInstance()
        findViewById<Button>(R.id.searchButton).setOnClickListener{search()}
        findViewById<Button>(R.id.loginButton).setOnClickListener{login()}
        findViewById<Button>(R.id.basketButton).setOnClickListener{basket()}
        findViewById<Button>(R.id.CatalogButton).setOnClickListener{catalog()}
    }
    private fun search(){
        val bruh = this
        var searchText = findViewById<EditText>(R.id.searchText).text
        if(searchText.toString()==""){
            return
        }
        if(searchText.length > 20){
            Toast.makeText(applicationContext,"Длинна запроса не может превышать 20 символов",Toast.LENGTH_SHORT).show()
        }
        val searched = GlobalScope.launch{
            val client = HttpClient(CIO)
            try {
                val response = client.get<HttpResponse>("https://backend.pulya.market/api/v1/product-cards-search?name="+searchText.toString().encodeURLPath()).readText()
                val intent = Intent(bruh, Search::class.java)
                intent.putExtra("text", searchText.toString())
                startActivity(intent)
            } catch (cause: Throwable) {
                println(cause.message)
                val intent = Intent(bruh, nothingFound::class.java)
                intent.putExtra("text", searchText.toString())
                startActivity(intent)
            }


        }

    }

    private fun login(){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }
    private fun basket(){
        val intent = Intent(this, basket::class.java)
        startActivity(intent)
    }
    private fun catalog(){startActivity(Intent(this, Catalog::class.java))}
}