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
import geekfed.com.drawrur.helper.RandomColor
import io.reactivex.Observable
import io.reactivex.subjects.ReplaySubject
import io.reactivex.subjects.Subject
import java.util.*

/**
 * @author cody
 */
class DrawingModel(drawingIntent: DrawingIntent) {

    private val randomColor = RandomColor()

    private val collectedDrawingPoints: ArrayList<DrawingPoint> = ArrayList()

    private val defaultColor = "#607D8B"

    private var previousState = DrawingState(emptyList(), defaultColor, false)

    private val subject: Subject<DrawingState> = ReplaySubject.create()

    init {
        Observable.merge(
            transformTouchesToState(drawingIntent.getTouches()),
            transformColorClicks(drawingIntent.getColorClicks()),
            transformResetClicks(drawingIntent.getResetClicks())
        ).subscribe {
            if (previousState != it) {
                previousState = it
                subject.onNext(it)
            }
        }
    }

    fun getObservable(): Observable<DrawingState> = subject

    private fun transformColorClicks(clicks: Observable<Boolean>): Observable<DrawingState> {
        return clicks.map {
            DrawingState(collectedDrawingPoints, randomColor.get(previousState.drawingColor), false)
        }
    }

    private fun transformResetClicks(clicks: Observable<Boolean>): Observable<DrawingState> {
        return clicks.map {
            collectedDrawingPoints.clear()
            DrawingState(collectedDrawingPoints, defaultColor, true)
        }
    }

    private fun transformTouchesToState(touches: Observable<DrawingPoint>): Observable<DrawingState> {
        return touches.map {
            var emitState = previousState
            collectedDrawingPoints.add(it)
            if (collectedDrawingPoints.size >= 2) {
                val currentDrawingPoint = collectedDrawingPoints.get(collectedDrawingPoints.size - 1)
                val previousDrawingPoint = collectedDrawingPoints.get(collectedDrawingPoints.size - 2)

                emitState = previousState.copy(
                        drawingPoints = listOf(previousDrawingPoint, currentDrawingPoint),
                        redraw = false
                )
            }
            emitState
        }
    }

}