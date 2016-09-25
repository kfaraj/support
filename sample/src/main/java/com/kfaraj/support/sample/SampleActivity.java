package com.kfaraj.support.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Demonstrates the features of the support library.
 */
public class SampleActivity extends AppCompatActivity {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            AppCompatFragment fragment = new AppCompatFragment();
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
        }
    }

}
