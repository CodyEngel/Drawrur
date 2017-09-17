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
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.widget.Button
import android.widget.LinearLayout
import com.jakewharton.rxbinding2.view.RxView
import geekfed.com.drawrur.R
import geekfed.com.drawrur.data.DrawingPoint
import io.reactivex.Observable

/**
 * @author cody
 */
class DrawingViewImpl(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
        LinearLayout(context, attrs, defStyleAttr, defStyleRes), DrawingView {

    private val paint = Paint()

    private var cachedBitmap: Bitmap? = null

    private val changeColorButton: Button

    private val resetButton: Button

    private val strokeWidth = 10f

    constructor(context: Context?) : this(context, null, 0, 0)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    init {
        changeColorButton = Button(context)
        resetButton = Button(context)
        placeChangeColorButton()
        placeResetButton()

        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeWidth = strokeWidth
    }

    override fun getMotionEvents(): Observable<MotionEvent> = RxView.touches(this)

    override fun getChangeColorClicks(): Observable<Any> = RxView.clicks(changeColorButton)

    override fun getResetClicks(): Observable<Any> = RxView.clicks(resetButton)

    fun render(drawingState: DrawingState) {
        if (!isAttachedToWindow) return

        if (cachedBitmap == null || drawingState.redraw) {
            cachedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }

        paint.color = Color.parseColor(drawingState.drawingColor)

        val drawingCanvas = Canvas(cachedBitmap)
        drawingCanvas.drawPath(generateDrawingPath(drawingState.drawingPoints), paint)
        background = BitmapDrawable(resources, cachedBitmap)
    }

    private fun generateDrawingPath(drawingPoints: List<DrawingPoint>): Path {
        val drawingPath = Path()
        var previousPoint: DrawingPoint? = null
        for (currentPoint in drawingPoints) {
            previousPoint?.let {
                if (currentPoint.time - it.time < 25) {
                    drawingPath.quadTo(it.x, it.y, currentPoint.x, currentPoint.y)
                } else {
                    drawingPath.moveTo(currentPoint.x, currentPoint.y)
                }
            } ?: drawingPath.moveTo(currentPoint.x, currentPoint.y)
            previousPoint = currentPoint
        }
        return drawingPath
    }

    private fun placeResetButton() {
        resetButton.text = context.getString(R.string.button_reset)
        placeButton(resetButton, Gravity.END)
    }

    private fun placeChangeColorButton() {
        changeColorButton.text = context.getString(R.string.button_change_color)
        placeButton(changeColorButton, Gravity.START)
    }

    private fun placeButton(button: Button, gravity: Int) {
        val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.BOTTOM + gravity
        params.weight = 1f
        addView(button, params)
    }
}