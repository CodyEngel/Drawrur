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

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.jakewharton.rxbinding2.view.RxView
import geekfed.com.drawrur.R
import geekfed.com.drawrur.data.DrawingPoint
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_drawing.*

class DrawingActivity : AppCompatActivity(), DrawingView {

    val paint = Paint()

    val baseStrokeWidth = 15f

    var cachedBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing)

        DrawingModel(DrawingIntent(this))
                .observable
                .subscribe {
                    render(it)
                }

    }

    fun render(drawingState: DrawingState) {
        if (cachedBitmap == null) {
            cachedBitmap = Bitmap.createBitmap(drawingCanvas.width, drawingCanvas.height, Bitmap.Config.ARGB_8888)
        }

        var previousPoint: DrawingPoint? = null
        for (currentPoint in drawingState.drawingPoints) {
            previousPoint?.let {
                val canvas = Canvas(cachedBitmap)
                paint.strokeWidth = baseStrokeWidth //+ (500f * currentPoint.size)
                paint.color = Color.BLACK
                canvas.drawLine(it.x, it.y, currentPoint.x, currentPoint.y, paint)
                drawingCanvas.background = BitmapDrawable(resources, cachedBitmap)
            }
            previousPoint = currentPoint
        }
    }

    override fun getMotionEvents(): Observable<MotionEvent> {
        return RxView.touches(drawingCanvas)
    }
}