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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.example.quickbuy.ui.CategoriesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categoryList[position]

        holder.itemView.setOnClickListener {
            if (category != null) {
                onCategoryClick(category)
            }
        }

        holder.apply {
            textView.text = category
        }
        when (category) {
            "Jewellery" -> holder.imageView.setImageResource(R.drawable.ic_shirt)
            // Add more cases for other categories
            else -> holder.imageView.setImageResource(R.drawable.ic_pant)
        }
    }

    override fun getItemCount(): Int = categoryList.size

}