package com.cyberswift.fms.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.cyberswift.fms.R;
import com.cyberswift.fms.util.Util;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_splash);
        setContentView(R.layout.activity_splash);
        new SplashTimerTask().execute();
    }
    private class SplashTimerTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(3345);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(Util.fetchUserClass(SplashActivity.this) == null) {
                openLoginActivity();
            } else {
                if (Util.fetchUserClass(SplashActivity.this).getIsRemember()) {
                    openHomeActivity();
                } else {
                    openLoginActivity();
                }
            }

        }
    }

    public void openLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void openHomeActivity() {
        Intent intent = new Intent(SplashActivity.this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
