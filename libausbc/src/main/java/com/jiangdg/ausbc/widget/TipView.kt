/*
 * Copyright 2017-2023 Jiangdg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jiangdg.ausbc.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView

class TipView : AppCompatTextView {

    private val mHandler = Handler(Looper.getMainLooper())

    private var showAnim = ObjectAnimator.ofFloat(this, "alpha", 0.08f, 0.8f)
    private var hideAnim = ObjectAnimator.ofFloat(this, "alpha", 0.8f, 0f)

    private val mGoneRunnable = Runnable {
        hideAnim.start()
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context!!,
            attrs,
            defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context?) {
        hideAnim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator ) {
            }

            override fun onAnimationEnd(animation: Animator ) {
                if (this@TipView.visibility == View.VISIBLE) {
                    this@TipView.visibility = View.GONE
                }
            }

            override fun onAnimationCancel(animation: Animator ) {
            }

            override fun onAnimationStart(animation: Animator ) {
            }
        })
    }

    fun show(name: String, duration: Long = SHOW_DURATION) {
        if (hideAnim.isRunning || hideAnim.isStarted) {
            hideAnim.cancel()
        }

        text = name
        if (this@TipView.visibility != View.VISIBLE) {
            this@TipView.visibility = View.VISIBLE
        }

        if (showAnim.isPaused || !showAnim.isRunning || !showAnim.isStarted) {
            showAnim.start()
        }

        mHandler.removeCallbacksAndMessages(null)
        mHandler.postDelayed(mGoneRunnable, duration)
    }

    companion object {
        const val SHOW_DURATION = 1500L
    }
}