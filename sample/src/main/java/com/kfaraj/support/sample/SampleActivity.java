package com.kfaraj.support.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Demonstrates the features of the Support Library.
 */
public class SampleActivity extends AppCompatActivity implements OnClickListener, OnNavigationItemSelectedListener {

    /**
     * Argument containing the title.
     */
    private static final String KEY_TITLE = "title";

    private DrawerLayout mDrawerLayout;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        final NavigationView navigationView = findViewById(R.id.navigation_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        if (savedInstanceState == null) {
            MenuItem item = navigationView.getMenu().getItem(0);
            onNavigationItemSelected(item);
        } else {
            CharSequence title = savedInstanceState.getCharSequence(KEY_TITLE);
            setTitle(title);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        CharSequence title = getTitle();
        outState.putCharSequence(KEY_TITLE, title);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View v) {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();
        final Fragment fragment;
        switch (id) {
            case R.id.appcompat:
                fragment = AppCompatFragment.newInstance();
                break;
            case R.id.recyclerview:
                fragment = RecyclerViewFragment.newInstance();
                break;
            default:
                fragment = null;
                break;
        }
        if (fragment != null) {
            item.setChecked(true);
            setTitle(item.getTitle());
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else {
            return false;
        }
    }

}
