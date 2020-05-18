package com.kush.recipefinder.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kush.recipefinder.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        var fromListAdapter = intent.extras

        if (fromListAdapter != null) {
            var link = fromListAdapter.getString("Link")

            webview.loadUrl(link.toString())
        }

    }
}
