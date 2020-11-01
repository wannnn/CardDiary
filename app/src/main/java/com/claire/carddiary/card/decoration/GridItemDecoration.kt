package com.claire.carddiary.card.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.claire.carddiary.utils.px

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
        if (position == -1) return
        when(spanCount) {
            1 -> {
                outRect.left = margin
                outRect.right = margin
                when(position) {
                    0 -> {
                        outRect.top = 20.px
                        outRect.bottom = 10.px
                    }
                    else -> {
                        outRect.top = 10.px
                        outRect.bottom = 10.px
                    }
                }
            }
            2 -> {
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
                outRect.top = 16.px
                outRect.bottom = 16.px
            }
        }
    }
}