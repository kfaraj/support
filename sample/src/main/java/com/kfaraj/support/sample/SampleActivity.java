package com.kfaraj.support.sample;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;

/**
 * Demonstrates the features of the Support Library.
 */
public class SampleActivity extends AppCompatActivity
        implements OnClickListener, OnNavigationItemSelectedListener {

    private static final String KEY_TITLE = "title";

    private DrawerLayout mDrawerLayout;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        final MaterialToolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        final NavigationView navigationView = ActivityCompat.requireViewById(this, R.id.navigation);
        mDrawerLayout = ActivityCompat.requireViewById(this, R.id.drawer_layout);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            final MenuItem item = navigationView.getMenu().getItem(0);
            onNavigationItemSelected(item);
        } else {
            final CharSequence title = savedInstanceState.getCharSequence(KEY_TITLE);
            setTitle(title);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        final CharSequence title = getTitle();
        outState.putCharSequence(KEY_TITLE, title);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode) {
        super.onSupportActionModeStarted(mode);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSupportActionModeFinished(@NonNull ActionMode mode) {
        super.onSupportActionModeFinished(mode);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
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
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else {
            return false;
        }
    }

}
