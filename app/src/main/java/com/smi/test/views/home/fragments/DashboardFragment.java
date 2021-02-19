package com.smi.test.views.home.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.smi.test.R;
import com.smi.test.models.Brand;
import com.smi.test.views.authentifiction.AuthentificationActivity;
import com.smi.test.views.authentifiction.fragments.SignInFragment;
import com.smi.test.views.base.BaseActivity;
import com.smi.test.views.home.HomeActivity;
import com.smi.test.views.home.adapters.BrandAdapter;
import com.smi.test.views.home.adapters.BrandTabAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DashboardFragment extends Fragment {

    private static final String TAG = DashboardFragment.class.getName();

    @BindView(R.id.brand_tabs)
    TabLayout brandTab;

    @BindView(R.id.brands_vp)
    ViewPager brandsVP;

/*    @BindView(R.id.brands_rv)
    RecyclerView brandsRV;*/

    private static final String ARG_NAME = "brand_name";
    private static final String ARG_DESC = "brand_des";

    private BrandTabAdapter brandTabAdapter;
    private String name, desc;
    private FragmentActivity mContext;
    private BrandAdapter brandAdapter;
    private ArrayList<Brand> allBrandList;
    private ArrayList<Brand> brandPremiumList;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Brand brand;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(String name, String desc) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_DESC, desc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);

        initDataFromFirebase();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    // Add Fragments to Tab
    private void initBrandViewPager(ViewPager viewPager) {
        brandTabAdapter = new BrandTabAdapter(getChildFragmentManager());
        brandTabAdapter.addFragment(PremiumBrandsFragment.newInstance(brandPremiumList), getString(R.string.premium_brands));
        brandTabAdapter.addFragment(AllBrandsFragment.newInstance(allBrandList), getString(R.string.all_brands));
        viewPager.setAdapter(brandTabAdapter);
        //brandAdapter.notifyDataSetChanged();
    }


    private void initDataFromFirebase() {
        Log.d(TAG, "Getting all brands");
        BaseActivity.showProgressDialog(mContext);
        allBrandList = new ArrayList<>();
        brandPremiumList = new ArrayList<>();

        //allBrandsFromFireBaseQuery
        Query queryBrands = reference.child(getString(R.string.dbnode_brands))
                .orderByKey();

        //loadingAllBrandsFromFireBase
        queryBrands.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                BaseActivity.hideProgressDialog();
                for (DataSnapshot brandsSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "loadBrands:onDataChange: " + brandsSnapshot.toString());
                    HashMap<String, JSONObject> dataSnapshotValue = (HashMap<String, JSONObject>) brandsSnapshot.getValue();
                    String jsonString = new Gson().toJson(dataSnapshotValue);
                    Brand brand = new Gson().fromJson(jsonString, Brand.class);
                    allBrandList.add(brand);

                    if (brand.isPremium())
                        brandPremiumList.add(brand);
                }
                Log.d(TAG, "parsedBrands size => " + allBrandList.size() + " \n parsedBrands =>" + allBrandList.toString());
                Log.d(TAG, "parsedPremium size => " + brandPremiumList.size() + " \n parsedBrands =>" + brandPremiumList.toString());
                initBrandViewPager(brandsVP);
                brandTab.setupWithViewPager(brandsVP);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadBrands:onCancelled", databaseError.toException());
            }
        });


    }


}