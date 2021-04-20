package com.claire.carddiary.card.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.claire.carddiary.CardApplication

class GridItemDecoration(
    private val margin: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val spanCount = CardApplication.rvListType
        val position = parent.getChildAdapterPosition(view)

        if (position == -1) return

        when(spanCount) {
            1 -> {
                with(outRect) {
                    left = margin
                    right = margin
                    bottom = margin / 2
                    top = margin / 2
                }
            }
            2 -> {
                if (position.rem(2) == 0) {
                    with(outRect) {
                        left = margin
                        top = margin / 2
                        right = margin / 2
                        bottom = margin / 2
                    }
                } else {
                    with(outRect) {
                        left = margin / 2
                        top = margin / 2
                        right = margin
                        bottom = margin / 2
                    }
                }
            }
        }
    }
}