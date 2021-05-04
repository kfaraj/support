package com.kfaraj.support.recyclerview.sample;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * Demonstrates how to use the RecyclerView library.
 */
public class MainActivity extends AppCompatActivity
        implements OnClickListener {

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
        final FloatingActionButton fab = ActivityCompat.requireViewById(this, R.id.fab);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(this);
        if (savedInstanceState == null) {
            final MainFragment fragment = MainFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .setReorderingAllowed(true)
                    .commit();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View v) {
        final List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof OnClickListener) {
                ((OnClickListener) fragment).onClick(v);
            }
        }
    }

}
