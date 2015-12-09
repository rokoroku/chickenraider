package com.bung.bungapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bung.bungapp.core.ApiCaller;
import com.bung.bungapp.core.Callback;
import com.bung.bungapp.model.User;
import com.bung.bungapp.util.Async;

public class PartyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);

    }
}
