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

import geekfed.com.drawrur.fakes.DrawingViewFake
import geekfed.com.drawrur.mocks.MotionEventMock
import org.junit.Test

/**
 * @author cody
 */
class DrawingIntentTest {

    @Test
    fun testTouches() {
        val drawingViewFake = DrawingViewFake(arrayListOf(
                MotionEventMock(1.0f, 2.0f, 3.0f, 40).mock,
                MotionEventMock(1.1f, 2.1f, 3.1f, 41).mock,
                MotionEventMock(506.45f, 321.184f, 0.81742f, 56471842L).mock
        ))
        val drawingIntent = DrawingIntent(drawingViewFake)

        drawingIntent.touches.test()
                .assertValueAt(0, { it.x == 1.0f && it.y == 2.0f && it.size == 3.0f && it.time == 40L })
                .assertValueAt(1, { it.x == 1.1f && it.y == 2.1f && it.size == 3.1f && it.time == 41L })
                .assertValueAt(2, { it.x == 506.45f && it.y == 321.184f && it.size == 0.81742f && it.time == 56471842L})
                .assertValueCount(3)
                .assertNoErrors()
    }
}