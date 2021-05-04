package com.kfaraj.support.appcompat.sample;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Demonstrates how to use the AppCompat library.
 */
public class MainFragment extends Fragment {

    /**
     * Constructor.
     */
    public MainFragment() {
        super(R.layout.fragment_main);
    }

    /**
     * Creates a new instance of this fragment class.
     *
     * @return a new instance of this fragment class.
     */
    @NonNull
    public static MainFragment newInstance() {
        return new MainFragment();
    }

}
