package com.claire.carddiary.card.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration(
    private val space: Int,
    private val margin: Int,
    private val spanCount: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        outRect.top = 0
        outRect.bottom = 0
        when {
            position % spanCount == 0 -> {
                outRect.left = margin
                outRect.right = space / 2
            }
            position % spanCount == spanCount - 1 -> {
                outRect.left = space / 2
                outRect.right = margin
            }
            else -> {
                outRect.left = space / 2
                outRect.right = space / 2
            }
        }
    }
}