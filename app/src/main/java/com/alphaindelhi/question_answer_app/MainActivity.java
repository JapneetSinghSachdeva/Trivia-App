package com.alphaindelhi.question_answer_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alphaindelhi.question_answer_app.controller.Volley_Singleton;
import com.alphaindelhi.question_answer_app.model.Question;
import com.alphaindelhi.question_answer_app.utils.Prefs;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    //RequestQueue requestQueue;
    ArrayList<Question> questionArrayList = new ArrayList<>();
    TextView textView;
    TextView questionTextview,counterTextview, scoreTrackerTextview, highestScoreTextView;
    private int counter = 0;
    private Button trueButton, falseButton;
    private ImageButton prevButton, nextButton;
    private CardView cardView;
     int score_tracker = 0;
     int highest_score_tracker = 0;
     Prefs prefs;
     Button shareBtn;
//     private  int getCounter = counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendApiRqst();

        prefs = new Prefs(MainActivity.this);


        cardView = findViewById(R.id.cardView);
        textView = findViewById(R.id.textView);
        questionTextview = findViewById(R.id.questionTextview);
        counterTextview = findViewById(R.id.counterTextview);
        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        scoreTrackerTextview = findViewById(R.id.scoreTrackerTextview);
        highestScoreTextView = findViewById(R.id.highestScore);
        shareBtn = findViewById(R.id.shareBtn);

        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        //shareBtn.setOnClickListener(this);

        //Getting the current state or where the user left the app
//         getCounter = prefs.getstate();

        Log.d("SScore", "Onclick: "+prefs.getHighScore());
        highestScoreTextView.setText("Highest Score: "+String.valueOf(prefs.getHighScore()));


    }


    @Override
    protected void onPause()
    {
        prefs.saveHighScore(counter);
        prefs.getHighScore();
//        prefs.setState(getCounter);

        super.onPause();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.trueButton:
                checkAnswer(true);
                updateQuestion();
                break;

            case R.id.falseButton:
                checkAnswer(false);
                updateQuestion();
                break;

            case R.id.nextButton:
                prefs.saveHighScore(counter);
                //Log.d("Score", "Onclick: "+prefs.getHighScore());
                counter = (counter + 1) % questionArrayList.size();
            updateQuestion();
            //saveHighestScore();

                break;

            case R.id.prevButton: if(counter>0)
            {
                counter--;
                updateQuestion();
            }
                break;
        }


    }

    private void updateQuestion()
    {
        String question = questionArrayList.get(counter).getAnswer();
        questionTextview.setText(question);
        String update = counter+"/"+questionArrayList.size();
        //counterTextview.setText(counter); does not work.
        counterTextview.setText(update);
    }

    private void checkAnswer(Boolean userChoice)
    {
        boolean answer = questionArrayList.get(counter).getAnswerTrue();
        if(userChoice == answer)
        {
            fadeAnimation();
            Toast.makeText(this, "Right Answer",
                    Toast.LENGTH_SHORT).show();
           addScore();
           goNext();
        }
        else
        {
            Toast.makeText(this, "Wrong Answer",
                    Toast.LENGTH_SHORT).show();
            shakeAnimation();
            deductScore();
            //cardView.setCardBackgroundColor(Color.RED);
        }

    }

    //this method is used to increment the counter and will be used in
    //Check answer method so that when user gets a right
    // answer question is incremented.
    public void goNext()
    {
        counter++;
    }

//    public void setScore_tracker()
//    {
//        boolean answer = questionArrayList.get(counter).getAnswerTrue();
//        checkAnswer(answer);
//
//        if(score_tracker>0)
//        {
//            scoreTrackerTextview.setText(score_tracker);
//        }
//    }

    public void addScore()
    {
        score_tracker+=1;
        scoreTrackerTextview.setText(String.valueOf(score_tracker));
        setHighest_score_tracker();
    }


    public void deductScore()
    {
        if(score_tracker>0)
        {
            score_tracker-=1;
            scoreTrackerTextview.setText(String.valueOf(score_tracker));
        }
        else
        {
            score_tracker = 0;
            scoreTrackerTextview.setText(String.valueOf(score_tracker));
        }
    }

    public void setHighest_score_tracker()
    {
        if (score_tracker>highest_score_tracker)
        {
            highest_score_tracker = score_tracker;
            highestScoreTextView.setText(String.valueOf(highest_score_tracker));
        }
        else
        {
            highestScoreTextView.setText(String.valueOf(highest_score_tracker));
        }
    }

    public void shareButton(View view)
    {
        String highestScoreData = highestScoreTextView.getText().toString();
        String myScoreData = ""+highestScoreData+" My current score is: "+score_tracker;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, myScoreData);
        //intent.putExtra(Intent.)
        //intent.putExtra(Intent.EXTRA_TEXT, "Current Score: "+ score_tracker);
        intent.putExtra(Intent.EXTRA_SUBJECT, "This data is from TRIVIA app made by Japneet!");
        startActivity(intent);
    }


    private void fadeAnimation()
    {
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);

        animation.setDuration(200);
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);

        cardView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                cardView.setCardBackgroundColor(Color.WHITE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void shakeAnimation()
    {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.shake_animation);
        final CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public List<Question> sendApiRqst(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                // Log.d("Json", "Data: "+response);
                for(int i = 0; i<response.length(); i++)
                {
                    try {

                        Question question = new Question();
                        question.setAnswer(response.getJSONArray(i).getString(0));
                        question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));

                        //adding questions objects to our Arraylist
                        questionArrayList.add(question);
                        Log.d("Json", "Data: "+question);
                        // textView.append("Data is: "+question+"\n");
                        questionTextview.setText(questionArrayList.get(counter).getAnswer());

//                        Log.d("Json", "Data: "+response.getJSONArray(i).
//                                getString(0));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Json", "Data: "+error);
            }
        });

        //requestQueue.add(jsonArrayRequest);
        //Volley_Singleton.getInstance().addRequestQueue(jsonArrayRequest);
        //requestQueue.add(jsonArrayRequest);

        // Volley_Singleton.getInstance().addRequestQueue(jsonArrayRequest);

        Volley_Singleton.getInstance(this).addRequestQueue(jsonArrayRequest);

        return questionArrayList;

    }


}

