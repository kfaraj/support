package com.kfaraj.support.sample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Demonstrates the features of the appcompat library.
 */
public class AppCompatFragment extends Fragment {

    /**
     * Creates a new instance of this fragment class.
     *
     * @return a new instance of this fragment class.
     */
    public static AppCompatFragment newInstance() {
        return new AppCompatFragment();
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appcompat, container, false);
    }

}
