package com.example.umonshoraire

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar

open class UImanager {

    /**
     * Run Runnable on UI
     * @param runnable Runnable to run on UI thread
     */
    protected fun runOnUiThread(runnable: Runnable?) {
        val uiHandler = Handler(Looper.getMainLooper())
        uiHandler.post(runnable!!)
    }

    /**
     * Create a Runnable object usable on UI thread to change spinner state
     * @param bool Set the spinner state
     * @return Runnable object
     */
    protected fun updateSpinnerRunnable(bool: Boolean, activity: Activity): Runnable {
        return Runnable {
            activity.findViewById<View>(R.id.menu).isEnabled = bool
        }
    }

    protected fun updateButtons(state: Boolean, activity: Activity): Runnable {
        return Runnable {
            val buttonUpdate =
                activity.findViewById<Button>(R.id.button_update)
            val buttonSearch =
                activity.findViewById<Button>(R.id.button_search)
            buttonUpdate.isEnabled = state
            buttonSearch.isEnabled = state
        }
    }

    /**
     * Update progress bar on MainActivityUI every time request is made
     * @return Runnable Object
     */
    protected fun updateProgressBar(activity: Activity): Runnable {
        return Runnable {
            val progressBar = activity.findViewById<ProgressBar>(R.id.progressBar)
            progressBar.incrementProgressBy(1)
        }
    }

    /**
     * Reset progress bar on MainActivityUI
     * @return Runnable object
     */
    protected fun resetProgressBar(activity: Activity): Runnable {
        return Runnable {
            val progressBar = activity.findViewById<ProgressBar>(R.id.progressBar)
            progressBar.progress = 0
        }
    }
}