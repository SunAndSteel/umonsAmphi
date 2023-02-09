package com.example.umonshoraire;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class UImanager {

    /**
     * Run Runnable on UI
     * @param runnable Runnable to run on UI thread
     */
    protected static void runOnUiThread(Runnable runnable){
        final Handler UIHandler = new Handler(Looper.getMainLooper());
        UIHandler .post(runnable);
    }

    /**
     * Create a Runnable object usable on UI thread to change spinner state
     * @param bool Set the spinner state
     * @return Runnable object
     */
    protected Runnable updateSpinnerRunnable(boolean bool, Activity activity) {
        return () -> activity.findViewById(R.id.menu).setEnabled(bool);
    }

    protected Runnable updateButtons(boolean state, Activity activity) {
        return () -> {
            Button buttonUpdate = activity.findViewById(R.id.button_update);
            Button buttonSearch = activity.findViewById(R.id.button_search);
            buttonUpdate.setEnabled(state);
            buttonSearch.setEnabled(state);
        };
    }

    /**
     * Update progress bar on MainActivityUI every time request is made
     * @return Runnable Object
     */
    protected Runnable updateProgressBar(Activity activity) {
        return () -> {
            ProgressBar progressBar = activity.findViewById(R.id.progressBar);
            progressBar.incrementProgressBy(1);
        };
    }

    /**
     * Reset progress bar on MainActivityUI
     * @return Runnable object
     */
    protected Runnable resetProgressBar(Activity activity) {
        return () -> {
            ProgressBar progressBar = activity.findViewById(R.id.progressBar);
            progressBar.setProgress(0);
        };
    }
}
