package com.bung.bungapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bung.bungapp.core.ApiCaller;
import com.bung.bungapp.core.Callback;
import com.bung.bungapp.model.User;
import com.bung.bungapp.util.Async;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ApiCaller.checkSessionAlive(new Callback<User>() {
            @Override
            public void onSuccess(User result) {
                if(result != null) {
                    BaseApplication.setUser(result);
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure() {
                Async.main(SplashActivity.this::launchLoginActivity, 1500);
            }
        });
    }

    private void launchLoginActivity() {
        if(!isFinishing()) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
        }
    }
}
