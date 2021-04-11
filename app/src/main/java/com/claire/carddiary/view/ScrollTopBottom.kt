package com.claire.carddiary.view

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.claire.carddiary.R

class ScrollTopButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private val animationY by lazy {
        resources.getDimensionPixelSize(R.dimen.scroll_top_button_translation).toFloat()
    }
    private var scrollOffset = 0
        set(value) {
            animateScrollTopButton(value)
            field = value
        }
    init {
        initView()
    }
    override fun onSaveInstanceState() = SavedState(super.onSaveInstanceState())
        .apply { offset = scrollOffset }
    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)
        scrollOffset = savedState.offset
    }
    private fun initView() {
        translationY = animationY
        setImageResource(R.drawable.ic_scroll_top)
    }
    fun bindRecyclerView(recycler: RecyclerView) {
        recycler.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    scrollOffset += dy
                }
            }
        )
        setOnClickListener {
            recycler.stopScroll()
            when (recycler.layoutManager) {
                is LinearLayoutManager ->
                    (recycler.layoutManager as LinearLayoutManager).scrollToPosition(0)
                is GridLayoutManager ->
                    (recycler.layoutManager as GridLayoutManager).scrollToPosition(0)
            }
            scrollOffset = 0
        }
    }
    fun resetOffset() {
        scrollOffset = 0
    }
    private fun animateScrollTopButton(offset: Int) {
        if (offset > SCROLL_TOP_THRESHOLD) {
            if (tag == null || tag == false) {
                animate()
                    .setDuration(TRANSLATION_DURATION)
                    .translationY(0f)
                tag = true
            }
        } else if (tag == true) {
            animate()
                .setDuration(TRANSLATION_DURATION)
                .translationY(animationY)
            tag = false
        }
    }
    class SavedState : BaseSavedState {
        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel) = SavedState(source)
                override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
            }
        }
        var offset = 0
        constructor(superState: Parcelable?) : super(superState)
        private constructor(parcel: Parcel) : super(parcel) {
            offset = parcel.readInt()
        }
        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(offset)
        }
    }
    companion object {
        private const val SCROLL_TOP_THRESHOLD = 500
        private const val TRANSLATION_DURATION = 500L
    }
}