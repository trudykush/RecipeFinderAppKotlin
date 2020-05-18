package com.kush.recipefinder.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kush.recipefinder.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_row_recipe.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButton.setOnClickListener {
            var intent = Intent(this, RecipeList::class.java)
            var ingredient = IngredientsEDT.text.toString().trim()
            var search = searchTermEdt.text.toString().trim()

            intent.putExtra("Ingredient", ingredient)
            intent.putExtra("Search", search)
            startActivity(intent)
        }
    }
}
