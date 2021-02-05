package com.kfaraj.support.appcompat.sample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.appbar.MaterialToolbar;

/**
 * Demonstrates how to use the AppCompat library.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Constructor.
     */
    public MainActivity() {
        super(R.layout.activity_main);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final MaterialToolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, MainFragment.class, null)
                    .setReorderingAllowed(true)
                    .commit();
        }
    }

}
