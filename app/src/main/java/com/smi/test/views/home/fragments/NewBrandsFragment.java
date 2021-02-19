package com.smi.test.views.home.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.smi.test.R;
import com.smi.test.models.Brand;
import com.smi.test.models.Dashboard;
import com.smi.test.views.base.BaseActivity;
import com.smi.test.views.home.adapters.BrandAdapter;
import com.smi.test.views.home.adapters.DashboardAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchFragment extends Fragment {

    private static final String TAG = SearchFragment.class.getName();

    @BindView(R.id.search_bar)
    SearchView searchBar;

    @BindView(R.id.search_rv)
    RecyclerView searchRV;

    @BindView(R.id.hole_container)
    LinearLayout holeContainer;

    private Dashboard dashboard;
    private List<dashboard> dashboardList;
    private FragmentActivity mContext;
    private DashboardAdapter brandsAdapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    public SearchFragment() {
        // Required empty public constructor
    }


    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        holeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!"".equals(query)) {
                    searchBar.post(new Runnable() {
                        @Override
                        public void run() {
                            searchBar.clearFocus();
                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                brandsAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return view;
    }

    private void initDataFromFirebase(){
        Log.d(TAG, "Getting new brands");
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
                for (DataSnapshot brandsSnapshot: dataSnapshot.getChildren()) {
                    Log.d(TAG, "loadBrands:onDataChange: "+ brandsSnapshot.toString());
                    HashMap<String, JSONObject> dataSnapshotValue = (HashMap<String, JSONObject>) brandsSnapshot.getValue();
                    String jsonString = new Gson().toJson(dataSnapshotValue);
                    Brand brand = new Gson().fromJson(jsonString, Brand.class);
                    allBrandList.add(brand);

                    if (brand.isPremium())
                        brandPremiumList.add(brand);
                }
                Log.d(TAG, "parsedBrands size => "+ allBrandList.size() +" \n parsedBrands =>" + allBrandList.toString());
                Log.d(TAG, "parsedPremium size => "+ brandPremiumList.size() +" \n parsedBrands =>" + brandPremiumList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadBrands:onCancelled", databaseError.toException());
            }
        });


    }


    private void initSearchRecyclerView(){
        dashboardList = new ArrayList<>();
        searchRV.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL, false);
        contactsAdapter = new DashboardAdapter(mContext, dashboard);
        contactsRV.setAdapter(contactsAdapter);
    }



}