package maw.mobet.category

import android.graphics.Canvas
import android.graphics.Rect
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import maw.mobet.R
import splitties.resources.appDrawable
import kotlin.math.roundToInt

class MyItemDecoration : RecyclerView.ItemDecoration() {
    private var divider = appDrawable(R.drawable.divider)!!
    private val bounds = Rect()

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        drawVertical(c, parent)
        drawHorizontal(c, parent)
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()

        var childCount = parent.childCount
        if (parent.layoutManager is GridLayoutManager) {
            var leftItems = childCount % (parent.layoutManager as GridLayoutManager).spanCount
            if (leftItems == 0) {
                leftItems = (parent.layoutManager as GridLayoutManager).spanCount
            }
            childCount -= leftItems
        }

        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i) ?: return
            parent.getDecoratedBoundsWithMargins(child, bounds)
            val bottom = bounds.bottom + child.translationY.roundToInt()
            val top = bottom - divider.intrinsicHeight
            divider.setBounds(0, top, parent.width, bottom)
            divider.draw(canvas)
        }
        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()

        var childCount = parent.childCount
        if (parent.layoutManager is GridLayoutManager) {
            childCount = (parent.layoutManager as GridLayoutManager).spanCount
        }

        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i) ?: return
            parent.layoutManager!!.getDecoratedBoundsWithMargins(child, bounds)
            val right = bounds.right + child.translationX.roundToInt()
            val left = right - divider.intrinsicWidth
            divider.setBounds(left, 0, right, parent.height)
            divider.draw(canvas)
        }
        canvas.restore()
    }
}
// https://link.medium.com/k6O6eC1bB5