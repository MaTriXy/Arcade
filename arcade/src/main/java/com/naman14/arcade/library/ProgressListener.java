package com.naman14.arcade.library;

/**
 * Created by naman on 12/05/16.
 */
public interface ProgressListener {

    public void onUpdateProgress(String log, int currentIteration, int totalIterations);
}
