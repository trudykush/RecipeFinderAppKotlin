package com.kush.recipefinder.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kush.recipefinder.R
import com.kush.recipefinder.data.RecipeListAdapter
import com.kush.recipefinder.model.LEFT_LINK
import com.kush.recipefinder.model.QUERY
import com.kush.recipefinder.model.Recipe
import kotlinx.android.synthetic.main.activity_recipe_list.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class RecipeList : AppCompatActivity() {

    var volleyRequest: RequestQueue? = null
    var recipeList: ArrayList<Recipe>? = null

    var recipeListAdapter: RecipeListAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        var urlString: String? = null
        var fromIntent = intent.extras
        var ingredients = fromIntent?.getString("Ingredient")
        var search = fromIntent?.getString("Search")

        if (fromIntent != null && !(ingredients.equals(""))
                                && !search.equals("")) {

            var tempUrl = LEFT_LINK + ingredients + QUERY + search

            urlString = tempUrl
        } else {
            urlString = "http://www.recipepuppy.com/api/?i=onions,garlic&q=omelet&p=3"
        }

        recipeList = ArrayList<Recipe>()

        volleyRequest =  Volley.newRequestQueue(this)

        getRecipe(urlString)
    }

    fun getRecipe(url: String) {
        val recipeRequest = JsonObjectRequest(Request.Method.GET,
                            url,
                            Response.Listener {
                                response: JSONObject ->
                                try {
                                    var responseObject = response.getJSONArray("results")

                                    for (i in 0 until responseObject.length()) {
                                        var recObj = responseObject.getJSONObject(i)

                                        var title = recObj.getString("title")
                                        var href = recObj.getString("href")
                                        var ingredients = recObj.getString("ingredients")
                                        var thumbnail = recObj.getString("thumbnail")

                                        var recipe = Recipe()
                                        recipe.title = title
                                        recipe.link = href
                                        recipe.ingredients = ingredients
                                        recipe.thumbnail = thumbnail

                                        recipeList?.add(recipe)

                                        recipeListAdapter = RecipeListAdapter(recipeList!!, this)
                                        layoutManager = LinearLayoutManager(this)

                                        recyclerViewID.layoutManager = layoutManager
                                        recyclerViewID.adapter = recipeListAdapter

                                    }

                                    recipeListAdapter?.notifyDataSetChanged()

                                } catch (e: JSONException) {e.printStackTrace()}
                            },
                            Response.ErrorListener {
                                error: VolleyError? ->
                                try {
                                    Log.d("Error is: ", error.toString())
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            })

        volleyRequest!!.add(recipeRequest)
    }
}