package com.example.quickbuy.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemSpacingDecoration(private val horizontal: Int, private val vertical: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        // Apply spacing to all sides of each item
        outRect.left = horizontal
        outRect.right = horizontal
        outRect.top = vertical
        outRect.bottom = vertical
    }
}