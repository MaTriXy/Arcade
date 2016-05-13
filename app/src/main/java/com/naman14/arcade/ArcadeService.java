package com.naman14.arcade;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.naman14.arcade.library.Arcade;
import com.naman14.arcade.library.ArcadeBuilder;
import com.naman14.arcade.library.ArcadeUtils;
import com.naman14.arcade.library.listeners.IterationListener;
import com.naman14.arcade.library.listeners.ProgressListener;

/**
 * Created by naman on 14/05/16.
 */
public class ArcadeService extends IntentService {


    public ArcadeService() {
        super("ArcadeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ArcadeBuilder builder = new ArcadeBuilder(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        builder.setStyleimage(intent.getStringExtra("style_path"));
        builder.setContentImage(intent.getStringExtra("content_path"));
        builder.setModelFile(ArcadeUtils.getModelPath());
        builder.setProtoFIle(ArcadeUtils.getProtoPath());
        builder.setImageSize(Integer.parseInt(preferences.getString("preference_image_size", "128")));
        builder.setIterations(Integer.parseInt(preferences.getString("preference_iterations", "15")));
        builder.setContentWeight(Integer.parseInt(preferences.getString("preference_content_weight", "20")));
        builder.setStyleWeight(Integer.parseInt(preferences.getString("preference_style_weight", "200")));
        Arcade arcade = builder.build();
        arcade.initialize();
        arcade.setLogEnabled(true);
        arcade.setProgressListener(new ProgressListener() {
            @Override
            public void onUpdateProgress(final String log, int currentIteration, int totalIterations) {
//                if (logFragment != null)
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            logFragment.addLog(log);
//                        }
//                    });
            }
        });
        arcade.setIterationListener(new IterationListener() {
            @Override
            public void onIteration(int currentIteration, int totalIteration) {
                Log.d("iterations", String.valueOf(currentIteration) + " of " + String.valueOf(totalIteration));
            }
        });
        arcade.stylize();
    }
}
