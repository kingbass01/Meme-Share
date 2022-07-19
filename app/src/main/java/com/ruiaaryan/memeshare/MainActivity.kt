package com.ruiaaryan.memeshare

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var imgUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val srn = findViewById<EditText>(R.id.subredditName)
        val n = findViewById<Button>(R.id.nextButton)
        val pbar = findViewById<ProgressBar>(R.id.progressBar)
        srn.setOnEditorActionListener{
            _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                load()
                true
            }
            false
        }
        pbar.isVisible = false
        n.setOnClickListener {
            load()
        }
    }

    private fun load() {
        val srn = findViewById<EditText>(R.id.subredditName)
        val img = findViewById<ImageView>(R.id.imageView)
        val s = findViewById<Button>(R.id.shareButton)
        val pbar = findViewById<ProgressBar>(R.id.progressBar)
        pbar.isVisible = true
        MemeAPIservice.Memeapi.retrofitService.getPhoto(srn.text.toString()).enqueue(object: Callback<MemeResponse> {
            override fun onResponse(call: Call<MemeResponse>, response: Response<MemeResponse>) {
                if (response.isSuccessful){

                    if (srn.text.toString().isNotBlank() && !srn.text.toString().isDigitsOnly()) {
                        imgUrl = response.body()!!.url
                        Glide.with(this@MainActivity).load(imgUrl).into(img)
                        pbar.isVisible = false
                    }
                    else{
                        pbar.isVisible = false
                        srn.hint = "ENTER A SUBREDDIT NAME"
                        srn.setHintTextColor(Color.RED)
                    }

                    s.setOnClickListener{
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Hey Checkout this $imgUrl")
                            type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(sendIntent, null)
                        startActivity(shareIntent)

                    }
                }
                else{
                    pbar.isVisible = false
                    Toast.makeText(this@MainActivity,"Subreddit Not Found",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MemeResponse>, t: Throwable) {
                pbar.isVisible = false
                Toast.makeText(this@MainActivity,"Check Your Internet Connection",Toast.LENGTH_SHORT).show()
            }
        })
    }

}