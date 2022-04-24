package com.example.pulyamarket

import android.app.ActionBar
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Math.abs
import java.net.URL


class Search : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val requestText = intent.getStringExtra("text")
        val searchExitButton = findViewById<Button>(R.id.searchExitButton)
        searchExitButton.setOnClickListener{startActivity(Intent(this, MainActivity::class.java))}
        GlobalScope.launch{
            val client = HttpClient(CIO)
            val response = client.get<HttpResponse>("https://backend.pulya.market/api/v1/product-cards-search?name="+requestText?.encodeURLPath()).readText()
            val res = response.split(""""_embedded":{"items":[{"products_cards":[{"""")[1]
            var productNames = splitBy(res, "\"nameRu\":\"", "\",")
            var productPhotos = splitBy(res, "\"photos\":\"", "\"")
            var productIds = mutableListOf<String>()
            var timeLister: MutableList<String> = res.split("\"productCardId\":") as MutableList<String>
            timeLister.removeAt(0)
            timeLister.forEach{productIds.add(it.split(",")[0])}
            productIds.forEach{ println(it)}
            val layout = findViewById<LinearLayout>(R.id.searchLayout)
            productPhotos.forEachIndexed { index, s ->
                try {
                    val image = ImageView(this@Search)
                    image.layoutParams = LinearLayout.LayoutParams(layout.width, 500)
                    image.scaleType = ImageView.ScaleType.FIT_CENTER
                    val newurl = URL(s)
                    val img = BitmapFactory.decodeStream(newurl.openConnection().getInputStream())
                    withContext(Dispatchers.Main) {
                        image.setImageBitmap(img)
                        layout.addView(image)
                        val prodtext = TextView(this@Search)
                        prodtext.textSize = 20f
                        prodtext.text = productNames[index].unescapeUnicode()
                        layout.addView(prodtext)
                        image.setOnClickListener{val intent = Intent(this@Search, Product::class.java)
                            intent.putExtra("productId", productIds[index])
                            startActivity(intent)}
                    }
                } catch (cause: Throwable) {
                    println(cause.message)
                }
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
    fun String.unescapeUnicode() = replace("\\\\u([0-9A-Fa-f]{4})".toRegex()) {
        String(Character.toChars(it.groupValues[1].toInt(radix = 16)))
    }
}
