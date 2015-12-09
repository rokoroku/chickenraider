package com.bung.bungapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.bung.bungapp.util.Async;
import com.bung.bungapp.util.ViewUtils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String PARAM_REDIRECT_FRAGMENT = "REDIRECT";

    public static final String TAG_FRAGMENT_NEARBY = "NEARBY";
    public static final String TAG_FRAGMENT_SEARCH = "SEARCH";
    public static final String TAG_FRAGMENT_FRIEND = "FRIEND";
    public static final String TAG_FRAGMENT_PARRTY = "PARTY";
    private Fragment mNearbyFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Set the padding to match the Status Bar height
        ((View)mToolbar.getParent()).setPadding(0, ViewUtils.getStatusBarHeight(this), 0, 0);
        setTitle("내 주변");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_layout, getNearbyFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_nearby) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_layout, getNearbyFragment())
                    .commit();
            setTitle("내 주변");
            // Handle the camera action
        } else if (id == R.id.nav_friends) {
            setTitle("내 친구");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Fragment getNearbyFragment() {
        mNearbyFragment = getFragmentManager().findFragmentByTag(TAG_FRAGMENT_NEARBY);
        if (mNearbyFragment == null) {
            mNearbyFragment = NearbyFragment.newInstance(null);
        }
        return mNearbyFragment;
    }
}
