package com.smi.test.views.home.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smi.test.R;
import com.smi.test.models.Brand;
import com.smi.test.views.home.HomeActivity;
import com.smi.test.views.home.adapters.BrandAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AllBrandsFragment extends Fragment implements BrandAdapter.OnBrandClickListener {

    private static final String TAG = AllBrandsFragment.class.getName();
    private static final String allBrandArgs = "allBrandArgs";

    @BindView(R.id.brands_rv)
    RecyclerView brandsRV;

    private FragmentActivity mContext;
    private BrandAdapter brandAdapter;
    private List<Brand> allBrandList;

    public AllBrandsFragment() {
        // Required empty public constructor
    }

    public static AllBrandsFragment newInstance(ArrayList<Brand> allBrandList) {
        AllBrandsFragment fragment = new AllBrandsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(allBrandArgs, allBrandList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        if (getArguments() != null) {
            allBrandList = new ArrayList<>(getArguments().getParcelableArrayList(allBrandArgs));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_brands, container, false);
        ButterKnife.bind(this, view);

        initRecyclerView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void initRecyclerView() {
        allBrandList = new ArrayList<>(allBrandList);
        brandsRV.setLayoutManager(new GridLayoutManager(mContext, 3));
        brandAdapter = new BrandAdapter(mContext, allBrandList, this::onBrandClick);
        brandsRV.setAdapter(brandAdapter);
    }

    @Override
    public void onBrandClick(Brand brand) {
        ((HomeActivity) mContext).switchFragment(DetailBrandFragment.newInstance(brand), DetailBrandFragment.class.getName());
    }
}