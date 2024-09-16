package com.example.quickbuy.products

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.quickbuy.R
import com.example.quickbuy.data.products.Product

class ProductDetails : AppCompatActivity() {
    private lateinit var image: ImageView
    private lateinit var title: TextView
    private lateinit var price: TextView
    private lateinit var description: TextView
    private var product: Product? = null
    private lateinit var rating: RatingBar
    private lateinit var category: TextView
    private lateinit var backButton: ImageView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_details)

        product = intent.getParcelableExtra<Product>("PRODUCT")

        image = findViewById(R.id.image)
        title = findViewById(R.id.title)
        description = findViewById(R.id.description)
        price = findViewById(R.id.price)
        rating = findViewById(R.id.rating)
        category = findViewById(R.id.category)
        backButton = findViewById(R.id.backButton)

        image.load(product?.image) {
            crossfade(true)
        }

        title.text = product?.title
        price.text = "${product?.price} TK"
        description.text = product?.description
        rating.rating = (product?.rating?.rate?.toFloat() ?: null)!!
        category.text = getCategoryText(product?.category)

        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getCategoryText(category: String?): String {
        return when (category) {
            "jewelery" -> "Jewellery"
            "electronics" -> "Electronics"
            "men's clothing" -> "Men's Clothing"
            "women's clothing" -> "Women's Clothing"
            else -> "Other"
        }
    }
}