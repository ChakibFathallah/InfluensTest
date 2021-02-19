package com.smi.test.views.home.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.smi.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;



public class BrandsFragment extends Fragment {

    private static final String TAG = BrandsFragment.class.getName();


    public BrandsFragment() {
        // Required empty public constructor
    }

    public static BrandsFragment newInstance() {
        BrandsFragment fragment = new BrandsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brands, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}