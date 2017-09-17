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

package geekfed.com.drawrur.fakes

import geekfed.com.drawrur.data.DrawingPoint
import geekfed.com.drawrur.ui.DrawingIntent
import io.reactivex.Observable

/**
 * @author cody
 */
class DrawingIntentFake(
        private val touches: List<DrawingPoint> = emptyList(),
        private val colorClicks: List<Boolean> = emptyList(),
        private val resetClicks: List<Boolean> = emptyList()
) : DrawingIntent {
    override fun getTouches(): Observable<DrawingPoint> =
            Observable.fromArray(touches).flatMapIterable { it }

    override fun getColorClicks(): Observable<Boolean> =
            Observable.fromArray(colorClicks).flatMapIterable { it }

    override fun getResetClicks(): Observable<Boolean> =
            Observable.fromArray(resetClicks).flatMapIterable { it }
}