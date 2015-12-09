package com.bung.bungapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bung.bungapp.core.ApiCaller;
import com.bung.bungapp.core.Callback;
import com.bung.bungapp.model.Party;
import com.bung.bungapp.util.ViewUtils;
import com.google.android.gms.maps.model.LatLng;

public class RecruitingActivity extends AppCompatActivity {

    public static String ARG_PLACE_ID = "PLACE_ID";
    public static String ARG_PLACE_NAME = "PLACE_NAME";
    public static String ARG_PLACE_LATLNG = "PLACE_LATLNG";

    private String mPlaceId;
    private String mPlaceName;
    private LatLng mLatLng;

    private TextView mTitle;
    private TextView mLocation;
    private TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setPadding(0, ViewUtils.getStatusBarHeight(this), 0, 0);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle("파티 생성");

        mTitle = (TextView) findViewById(R.id.title);
        mLocation = (TextView) findViewById(R.id.location);
        mDescription = (TextView) findViewById(R.id.description);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mPlaceId = extras.getString(ARG_PLACE_ID);
            mPlaceName = extras.getString(ARG_PLACE_NAME);
            mLatLng = extras.getParcelable(ARG_PLACE_LATLNG);
        }
        if (mPlaceName != null) {
            mLocation.setText(mPlaceName);
            mLocation.setEnabled(false);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> postParty());
    }


    private void postParty() {
        String title = mTitle.getText().toString();
        String description = mDescription.getText().toString();

        View focusView = null;
        boolean cancel = false;

        // Check for a valid title address.
        if (TextUtils.isEmpty(title)) {
            mTitle.setError(getString(R.string.error_field_required));
            focusView = mTitle;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mLocation.getText().toString())) {
            mTitle.setError(getString(R.string.error_field_required));
            focusView = mTitle;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            Party party = new Party(title, description, mPlaceName, mPlaceId, mLatLng.latitude, mLatLng.longitude);
            ApiCaller.createParty(party, new Callback<Party>() {
                @Override
                public void onSuccess(Party result) {
                    finish();
                }

                @Override
                public void onFailure() {
                    Toast.makeText(RecruitingActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
