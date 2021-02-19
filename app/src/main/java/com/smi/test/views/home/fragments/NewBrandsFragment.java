package com.smi.test.views.home.fragments;

import android.app.Activity;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.smi.test.R;
import com.smi.test.models.Brand;
import com.smi.test.models.NewBrand;
import com.smi.test.views.base.BaseActivity;
import com.smi.test.views.home.HomeActivity;
import com.smi.test.views.home.adapters.NewBrandAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewBrandsFragment extends Fragment implements NewBrandAdapter.OnNewBrandClickListener {

    private static final String TAG = NewBrandsFragment.class.getName();

    @BindView(R.id.search_bar)
    SearchView searchBar;

    @BindView(R.id.search_rv)
    RecyclerView searchRV;

    @BindView(R.id.hole_container)
    LinearLayout holeContainer;

    private NewBrand newBrand;
    private List<NewBrand> newBrandList;
    private FragmentActivity mContext;
    private NewBrandAdapter newBrandAdapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    public NewBrandsFragment() {
        // Required empty public constructor
    }


    public static NewBrandsFragment newInstance() {
        NewBrandsFragment fragment = new NewBrandsFragment();
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
        initNewBrandsFromFirebase();

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
                newBrandAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return view;
    }

    private void initNewBrandsFromFirebase() {
        Log.d(TAG, "Getting new brands");
        BaseActivity.showProgressDialog(mContext);
        newBrandList = new ArrayList<>();

        //allBrandsFromFireBaseQuery
        Query queryBrands = reference.child(getString(R.string.dbnode_brands))
                .orderByKey();

        //loadingAllBrandsFromFireBase
        queryBrands.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                BaseActivity.hideProgressDialog();
                for (DataSnapshot brandsSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "loadNewBrands:onDataChange: " + brandsSnapshot.toString());
                    HashMap<String, JSONObject> dataSnapshotValue = (HashMap<String, JSONObject>) brandsSnapshot.getValue();
                    String jsonString = new Gson().toJson(dataSnapshotValue);
                    NewBrand newBrand = new NewBrand("", "", true);
                    newBrand = new Gson().fromJson(jsonString, NewBrand.class);
                    if (newBrand.isNew())
                        newBrandList.add(newBrand);
                }
                initSearchRecyclerView();
                Log.d(TAG, "parsedNewBrands size => " + newBrandList.size() + " \n parsedNewBrands =>" + newBrandList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadBrands:onCancelled", databaseError.toException());
            }
        });

    }


    private void initSearchRecyclerView() {
        searchRV.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        newBrandAdapter = new NewBrandAdapter(mContext, newBrandList, this::onNewBrandClick);
        searchRV.setAdapter(newBrandAdapter);
    }


    @Override
    public void onNewBrandClick(NewBrand newBrand) {
        ((HomeActivity) mContext).switchFragment(DetailBrandFragment.newInstanceForNewBrand(newBrand), DetailBrandFragment.class.getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        mContext = getActivity();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }
}