package com.example.quickbuy.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.quickbuy.R
import com.example.quickbuy.data.products.Product
import com.example.quickbuy.data.products.ProductsResponse

class ProductsAdapter(
    private val productList: ProductsResponse,
    private val onItemClick: (Product) -> Unit
) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.productName)
        val price: TextView = itemView.findViewById(R.id.productPrice)
//        val description: TextView = itemView.findViewById(R.id.productDescription)
        val image: ImageView = itemView.findViewById(R.id.productImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]

        holder.itemView.setOnClickListener { onItemClick(product) }

        holder.apply {
            title.text = product.title
            price.text = "${product.price} TK"
//            description.text = product.description
            image.load(product.image) {
                crossfade(true)
            }
            // Assign a unique transition name using the product id or position
            image.transitionName = "product_image_$position"

        }
    }

    override fun getItemCount(): Int = productList.size

}