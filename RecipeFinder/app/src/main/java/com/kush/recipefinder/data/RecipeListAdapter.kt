package com.kush.recipefinder.data

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kush.recipefinder.R
import com.kush.recipefinder.activities.WebViewActivity
import com.kush.recipefinder.model.Recipe
import com.squareup.picasso.Picasso

class RecipeListAdapter(val list:ArrayList<Recipe>,
                       val context: Context): RecyclerView.Adapter<RecipeListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(context)
            .inflate( R.layout.list_row_recipe, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindView(list[position])
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var title = itemView.findViewById<TextView>(R.id.recipeTitle)
        var thumbnail = itemView.findViewById<ImageView>(R.id.thumbnail)
        var ingredients = itemView.findViewById<TextView>(R.id.recipeIngredients)
        var linkButton = itemView.findViewById<Button>(R.id.linkButton)

        fun bindView(recipe: Recipe) {
            title.text = recipe.title
            ingredients.text = recipe.ingredients
            linkButton.text = recipe.link

            if (!TextUtils.isEmpty(recipe.thumbnail)) {
                Picasso.with(context)
                    .load(recipe.thumbnail)
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(thumbnail)
            } else {
                Picasso.with(context).load(R.mipmap.ic_launcher).into(thumbnail)
            }

            linkButton.setOnClickListener {
                var intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra("Link", recipe.link)
                context.startActivity(intent)
            }
        }

    }

}