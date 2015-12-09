package com.bung.bungapp.util;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by rok on 2015. 5. 29..
 */

public class Async {

    private static Handler sHandler;
    private static final int[] DRAWABLE_EMPTY_STATE = new int[]{};

    public static void main(final Runnable runnable) {
        Looper mainLooper = Looper.getMainLooper();
        if (mainLooper.getThread() == Thread.currentThread()) {
            runnable.run();
        } else {
            if (sHandler == null) sHandler = new Handler(mainLooper);
            sHandler.post(runnable);
        }
    }

    public static void main(final Runnable runnable, long delay) {
        if (sHandler == null) sHandler = new Handler(Looper.getMainLooper());
        sHandler.postDelayed(runnable, delay);
    }

    public static void background(final Runnable runnable) {
        AsyncTask.execute(runnable);
    }

    public static void background(final Runnable runnable, long delay) {
        main(new Runnable() {
            @Override
            public void run() {
                AsyncTask.execute(runnable);
            }
        }, delay);
    }
}