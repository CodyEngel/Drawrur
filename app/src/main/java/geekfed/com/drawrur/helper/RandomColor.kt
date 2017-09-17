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

package geekfed.com.drawrur.helper

/**
 * @author cody
 */
class RandomColor {

    fun get(previousColor: String): String {
        val newColor = when ((0..19).random()) {
            0 -> "#F44336"
            1 -> "#E91E63"
            2 -> "#9C27B0"
            3 -> "#673AB7"
            4 -> "#3F51B5"
            5 -> "#2196F3"
            6 -> "#03A9F4"
            7 -> "#00BCD4"
            8 -> "#009688"
            9 -> "#4CAF50"
            10 -> "#8BC34A"
            11 -> "#CDDC39"
            12 -> "#FFEB3B"
            13 -> "#FFC107"
            14 -> "#FF9800"
            15 -> "#FF5722"
            16 -> "#795548"
            17 -> "#9E9E9E"
            18 -> "#607D8B"
            else -> get(previousColor)
        }

        if (newColor == previousColor) {
            return get(previousColor)
        } else {
            return newColor
        }
    }
}