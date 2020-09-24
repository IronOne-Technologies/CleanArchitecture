package com.ironone.presentation.common

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ironone.presentation.R

abstract class SwipeToActionCallback internal constructor(var context: Context):
    ItemTouchHelper.Callback() {

    private val clearPaint: Paint = Paint()
    private val background: ColorDrawable = ColorDrawable()
    private val bgColor: Int = context.resources.getColor(R.color.bgColorDelete, null)
    private val drawable: Drawable?
    private val intrinsicWidth: Int
    private val intrinsicHeight: Int

    init {
        clearPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        drawable = ContextCompat.getDrawable(context, R.drawable.ic_delete)
        intrinsicWidth = drawable!!.intrinsicWidth
        intrinsicHeight = drawable.intrinsicHeight
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(
            0,
            ItemTouchHelper.LEFT
        )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        viewHolder1: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        val itemHeight = itemView.height
        val isCancelled = dX == 0f && !isCurrentlyActive
        if (isCancelled) {
            clearCanvas(
                c,
                itemView.right + dX,
                itemView.top.toFloat(),
                itemView.right.toFloat(),
                itemView.bottom.toFloat()
            )
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }
        background.color = bgColor
        background.setBounds(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        background.draw(c)
        val actionIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val actionIconMargin = (itemHeight - intrinsicHeight) / 2
        val actionIconLeft = itemView.right - actionIconMargin - intrinsicWidth
        val actionIconRight = itemView.right - actionIconMargin
        val actionIconBottom = actionIconTop + intrinsicHeight
        drawable!!.setBounds(actionIconLeft, actionIconTop, actionIconRight, actionIconBottom)
        drawable.draw(c)
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(
        c: Canvas,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float
    ) {
        c.drawRect(left, top, right, bottom, clearPaint)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.7f
    }
}