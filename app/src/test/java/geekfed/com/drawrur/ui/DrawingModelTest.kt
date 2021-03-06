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
import geekfed.com.drawrur.fakes.DrawingIntentFake
import org.junit.Test

/**
 * @author cody
 */
class DrawingModelTest {

    @Test
    fun testTouches() {
        val drawingIntentFake = DrawingIntentFake(
                touches = arrayListOf(
                        DrawingPoint(1.2f, 2.2f, 3.2f, 42),
                        DrawingPoint(506.45f, 321.184f, 0.81742f, 43),
                        DrawingPoint(506.45f, 321.184f, 0.81742f, 45)
                )
        )
        val drawingModel = DrawingModel(drawingIntentFake)

        drawingModel.getObservable().test()
                .assertValueAt(0, {
                    it.drawingPoints.size == 2 &&
                    it.drawingPoints.get(0).time == 42L &&
                    it.drawingPoints.get(1).time == 43L
                })
                .assertValueAt(1, {
                    it.drawingPoints.size == 2 &&
                            it.drawingPoints.get(0).time == 43L &&
                            it.drawingPoints.get(1).time == 45L
                })
                .assertValueCount(2)
                .assertNoErrors()
    }
}