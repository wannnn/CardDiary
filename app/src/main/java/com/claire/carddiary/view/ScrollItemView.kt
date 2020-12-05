package com.claire.carddiary.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.annotation.LayoutRes

class ScrollItemView(
    context: Context?,
    attrs: AttributeSet?
) : HorizontalScrollView(context, attrs) {

    private val root: LinearLayout = LinearLayout(context).apply {
        setBackgroundColor(Color.WHITE)
        orientation = LinearLayout.HORIZONTAL
    }

    init {
        addView(root, LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ))
    }

    fun setData(@LayoutRes layoutId: Int, data: List<Any>) {

        root.removeAllViews()

        data.forEachIndexed { index, scrollItemData ->
            ScrollItemViewHolder(root, layoutId)
                .bind(scrollItemData)
                .setOnClickListener {

                }
                .addViewToParent()
        }

    }
}