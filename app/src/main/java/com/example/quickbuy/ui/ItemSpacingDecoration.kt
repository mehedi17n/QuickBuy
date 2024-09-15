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

        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0

        // Apply horizontal spacing to all sides
        outRect.left = horizontal
        outRect.right = horizontal

        // Apply vertical spacing to top and bottom
        if (position == 0) {
            // First item, no top spacing
            outRect.top = 0
        } else {
            outRect.top = vertical
        }

        // Apply vertical spacing to bottom of each item
        if (position == itemCount - 1) {
            // Last item, no bottom spacing (optional)
            outRect.bottom = 0
        } else {
            outRect.bottom = vertical
        }
    }
}

//class ItemSpacingDecoration(private val horizontal: Int, private val vertical: Int) :
//    RecyclerView.ItemDecoration() {
//    override fun getItemOffsets(
//        outRect: Rect,
//        view: View,
//        parent: RecyclerView,
//        state: RecyclerView.State
//    ) {
//        super.getItemOffsets(outRect, view, parent, state)
//
//        // Apply spacing to all sides of each item
//        outRect.left = horizontal
//        outRect.right = horizontal
//        outRect.top = vertical
//        outRect.bottom = vertical
//    }
//}