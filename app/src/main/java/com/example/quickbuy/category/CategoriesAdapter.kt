package com.example.quickbuy.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbuy.R

class CategoriesAdapter(
    private var categoryList: List<String?>,
    private val onCategoryClick: (String) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.categoryImage)
        val textView: TextView = view.findViewById(R.id.categoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categoryList[position]

        // Set the click listener
        holder.itemView.setOnClickListener {
            category?.let {
                onCategoryClick(it)
            }
        }

        // Get both the icon and text for the category
        val (iconRes, categoryName) = getCategoryInfo(category)

        // Bind the text and image for each category
        holder.textView.text = categoryName
        holder.imageView.setImageResource(iconRes)
    }

    override fun getItemCount(): Int = categoryList.size

    // Helper function to map categories to icons and text
    private fun getCategoryInfo(category: String?): Pair<Int, String> {
        return when (category) {
            "jewelery" -> Pair(R.drawable.ic_jewelry, "Jewellery")
            "electronics" -> Pair(R.drawable.ic_electronics, "Electronics")
            "men's clothing" -> Pair(R.drawable.ic_shirt, "Men's\nClothing")
            "women's clothing" -> Pair(R.drawable.ic_dress, "Women's\nClothing")
            // Add more categories with appropriate icons and names
            else -> Pair(R.drawable.ic_other, "Other") // Default icon and text
        }
    }
}
