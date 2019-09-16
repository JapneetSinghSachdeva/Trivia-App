package com.alphaindelhi.question_answer_app.utils;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {

    SharedPreferences preferences;

    public Prefs(Activity activity) {
        this.preferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void saveHighScore(int score)
    {
        int currentScore = score;

        int lastScore = preferences.getInt("highScore", 0);

        if(currentScore>lastScore)
        {
            //new higher score:
            preferences.edit().putInt("highScore", currentScore).apply();
        }
    }

    public int getHighScore()
    {
        return preferences.getInt("highScore", 0);
    }

    public void setState(int index)
    {
        preferences.edit().putInt("index", index).apply();
    }

    public int getstate()
    {
        return preferences.getInt("index", 0);
    }

}
