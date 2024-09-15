package com.example.quickbuy.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.quickbuy.R
import com.example.quickbuy.ui.ProductsAdapter.ViewHolder

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

        // Bind the text and image for each category
        holder.textView.text = category
        holder.imageView.setImageResource(getCategoryIcon(category))
    }

    override fun getItemCount(): Int = categoryList.size

    // Helper function to map categories to icons
    private fun getCategoryIcon(category: String?): Int {
        return when (category) {
            "Jewellery" -> R.drawable.ic_pant
            "Electronics" -> R.drawable.ic_shirt
            "Clothing" -> R.drawable.ic_shirt
            "Shoes" -> R.drawable.ic_shirt
            // Add more categories with appropriate icons
            else -> R.drawable.ic_shirt // Default icon
        }
    }
}
