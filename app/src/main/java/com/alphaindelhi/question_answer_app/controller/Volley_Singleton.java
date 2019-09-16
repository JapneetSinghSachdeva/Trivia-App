package com.alphaindelhi.question_answer_app.controller;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Volley_Singleton {


    private static Volley_Singleton mInstance;
    private RequestQueue mRequestQueue;
    private Context context;


    public Volley_Singleton(Context context) {
        this.context = context;
        mRequestQueue = getRequestQueue();
    }

    public void addRequestQueue(Request request) {
        mRequestQueue.add(request);
    }

    public static synchronized Volley_Singleton getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new Volley_Singleton(context);
        }
        return mInstance;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return mRequestQueue;


    }
}
