package com.example.pulyamarket

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import java.net.URL

class Product : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        val productId = intent.getStringExtra("productId")
        val client = HttpClient(CIO)
        val res: String = runBlocking{client.get<HttpResponse>("https://backend.pulya.market/api/v1/product-cards/$productId").readText()}
        val price = splitBy(res, "\"price\":", ",")[0]
        val name = splitBy(res, "\"nameRu\":\"", "\",")[0].unescapeUnicode()
        findViewById<TextView>(R.id.productName).text = name
        val photoUrl = splitBy(res, "\"photos\":[\"", "\"")[0]
        val productImageView = findViewById<ImageView>(R.id.productImage)
        GlobalScope.launch{
            val productBitmap = BitmapFactory.decodeStream(URL(photoUrl).openConnection().getInputStream())
            withContext(Dispatchers.Main) {
                productImageView.setImageBitmap(productBitmap)
            }
        }
    }
    fun splitBy(text: String, by: String, end: String): MutableList<String> {
        var results = mutableListOf<String>()
        var first = true
        text.split(by).forEach{
            if(!first) {
                val thet = it.split(end)[0]
                results.add(thet)
            } else { first = false }
        }
        return results
    }
    fun String.unescapeUnicode() = replace("\\\\u([0-9A-Fa-f]{4})".toRegex()) {String(Character.toChars(it.groupValues[1].toInt(radix = 16))) }
}