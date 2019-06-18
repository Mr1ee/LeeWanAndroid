package com.lee.leewanandroid.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lee.leewanandroid.R

/**
 * 类描述：通用ItemDecoration
 * @param color 颜色，默认cbase_gray_color 灰色
 * @param space 间隔高度，默认1dp
 * @param showLast 是否显示最后一项的分隔栏，默认不展示
 * @param padding divider的左右边距，默认没有边距
 * @param ignores 忽略哪几项，默认都不忽略
 *
 * @version: 1.0.0
 * @author: jiayuanmin
 * @time: 2019/2/26
 */
class EasyItemDecoration(
    context: Context, @ColorRes var color: Int = R.color.line_divider_color,
    private var space: Int = context.resources.getDimensionPixelOffset(R.dimen.dp_1),
    private var showLast: Boolean = false,
    private var padding: Int = 0,
    private var ignores: List<Int> = emptyList()
) : RecyclerView.ItemDecoration() {

    private var mPaint: Paint = Paint()
    private val mBounds = Rect()


    init {
        mPaint.color = ContextCompat.getColor(context, color)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val count = if (showLast) {
            parent.layoutManager!!.itemCount
        } else {
            parent.layoutManager!!.itemCount - 1
        }
        val pos = parent.getChildAdapterPosition(view)
        if (pos < count && !ignores.contains(pos)) {
            outRect.bottom = space
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val count = if (!showLast) {
            parent.childCount - 1
        } else {
            parent.childCount
        }
        val left = parent.paddingLeft.toFloat() + padding
        val right = parent.width - parent.paddingRight.toFloat() - padding
        for (i in 0 until count) {
            val view = parent.getChildAt(i)
            if (ignores.contains(parent.getChildAdapterPosition(view))) {
                continue
            }
            //考虑到子view的margin
            parent.getDecoratedBoundsWithMargins(view, mBounds)
            val bottom = mBounds.bottom.toFloat()
            val top = bottom - space
            c.drawRect(left, top, right, bottom, mPaint)
        }

    }
}