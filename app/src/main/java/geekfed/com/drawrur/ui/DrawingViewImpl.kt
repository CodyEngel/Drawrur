/*
 * Copyright (c) 2017 Cody Engel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package geekfed.com.drawrur.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.jakewharton.rxbinding2.view.RxView
import geekfed.com.drawrur.data.DrawingPoint
import io.reactivex.Observable

/**
 * @author cody
 */
class DrawingViewImpl(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
        FrameLayout(context, attrs, defStyleAttr, defStyleRes), DrawingView {

    val paint = Paint()

    val baseStrokeWidth = 15f

    var cachedBitmap: Bitmap? = null

    constructor(context: Context?) : this(context, null, 0, 0)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    init {
        paint.strokeWidth = baseStrokeWidth
    }

    fun render(drawingState: DrawingState) {
        if (cachedBitmap == null) {
            cachedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }

        var previousPoint: DrawingPoint? = null
        for (currentPoint in drawingState.drawingPoints) {
            previousPoint?.let {
                val canvas = Canvas(cachedBitmap)
                canvas.drawLine(it.x, it.y, currentPoint.x, currentPoint.y, paint)
                background = BitmapDrawable(resources, cachedBitmap)
            }
            previousPoint = currentPoint
        }
    }

    override fun getMotionEvents(): Observable<MotionEvent> {
        return RxView.touches(this)
    }

}