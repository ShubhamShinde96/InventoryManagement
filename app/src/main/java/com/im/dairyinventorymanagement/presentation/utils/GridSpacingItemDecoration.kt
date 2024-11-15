package com.im.dairyinventorymanagement.presentation.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val verticalSpacing: Int,
    private val horizontalSpacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        // Apply vertical spacing
        outRect.top = verticalSpacing
        outRect.bottom = verticalSpacing

        // Apply horizontal spacing
        outRect.left = horizontalSpacing
        outRect.right = horizontalSpacing
    }
}