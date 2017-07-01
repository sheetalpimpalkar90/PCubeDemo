package com.spimpalkar.pcubedemo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spimpalkar.pcubedemo.R;

/**
 * Created by manish on 7/1/17.
 */

public class TopDealsFragment extends Fragment {

    public TopDealsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deals, container, false);
    }
}
