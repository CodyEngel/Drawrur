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

import geekfed.com.drawrur.data.DrawingPoint
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * @author cody
 */
class DrawingModel(drawingIntent: DrawingIntent) {

    private val collectedDrawingPoints: ArrayList<DrawingPoint> = ArrayList()

    private val subject: BehaviorSubject<DrawingState> = BehaviorSubject.create()

    val observable: Observable<DrawingState> = subject

    init {
        drawingIntent.touches.subscribe {
            collectedDrawingPoints.add(it)
            if (collectedDrawingPoints.size >= 2) {
                val currentDrawingPoint = collectedDrawingPoints.get(collectedDrawingPoints.size - 1)
                val previousDrawingPoint = collectedDrawingPoints.get(collectedDrawingPoints.size - 2)

                if (currentDrawingPoint.time - previousDrawingPoint.time < 50) {
                    subject.onNext(
                            DrawingState(
                                    listOf(previousDrawingPoint, currentDrawingPoint)
                            )
                    )
                }
            }
        }
    }

}