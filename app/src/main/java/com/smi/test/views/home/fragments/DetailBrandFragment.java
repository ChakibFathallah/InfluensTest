package com.smi.test.views.home.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.smi.test.R;
import com.smi.test.models.Brand;
import com.smi.test.models.Dashboard;
import com.smi.test.models.Purchase;
import com.smi.test.views.base.BaseActivity;
import com.smi.test.views.home.adapters.DashboardAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailBrandFragment extends Fragment {

    private static final String TAG = DetailBrandFragment.class.getName();

    @BindView(R.id.img)
    ShapeableImageView img;

    @BindView(R.id.name)
    TextView nameTV;

    @BindView(R.id.desc)
    TextView descTV;

    @BindView(R.id.dashboard_rv)
    RecyclerView dashboardRV;


    private static final String ARG_BRAND = "brandObject";
    private static final String ARG_NEW_BRAND = "newBrandObject";

    private FragmentActivity mContext;
    private Brand brand;
    private Dashboard newBrand;
    private List<Dashboard> dashboardList;
    private DashboardAdapter dashboardAdapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String commission = "";


    public DetailBrandFragment() {
        // Required empty public constructor
    }

    public static DetailBrandFragment newInstance(Brand brand) {
        DetailBrandFragment fragment = new DetailBrandFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BRAND, brand);
        fragment.setArguments(args);
        return fragment;
    }

    public static DetailBrandFragment newInstanceForNewBrand(Dashboard newBrand) {
        DetailBrandFragment fragment = new DetailBrandFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NEW_BRAND, newBrand);
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
            brand = getArguments().getParcelable(ARG_BRAND);
            newBrand = getArguments().getParcelable(ARG_NEW_BRAND);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_brand, container, false);
        ButterKnife.bind(this, view);

        if (brand != null){
            nameTV.setText(brand.getName());
            descTV.setText(brand.getDescription());
            Glide.with(mContext).load(brand.getPic()).into(img);
        } else if (newBrand != null){
            nameTV.setText(newBrand.getName());
            descTV.setText("Le lorem ipsum est, en imprimerie, une suite de mots sans signification utilisée à titre provisoire pour calibrer une mise en");
            Glide.with(mContext).load(newBrand.getPic()).into(img);
        }

        initRecyclerView();




        initDataFromFirebase();

        return view;
    }

    private void initDataFromFirebase() {
        Log.d(TAG, "Getting all Dashboard");
        // BaseActivity.showProgressDialog(mContext);

        //queryPurchaseByOfferId
        Query queryPurchase = reference.child(getString(R.string.dbnode_conversions))
                .child(getString(R.string.dbnode_purchase));
             /*   .child("offerId")
                .equalTo(brand.getOfferId());*/

        //LoadingDashboardFromFireBase
        queryPurchase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot purchaseSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "loadPurchase:onDataChange: " + purchaseSnapshot.toString());
                    HashMap<String, JSONObject> dataSnapshotValue = (HashMap<String, JSONObject>) purchaseSnapshot.getValue();
                    String jsonString = new Gson().toJson(dataSnapshotValue);
//                  Purchase purchase = new Gson().fromJson(jsonString, Purchase.class);
                }
                Log.d(TAG, "parsedPurchase size => " + dashboardList.size() + " \n parsedPurchase =>" + dashboardList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPurchase:onCancelled", databaseError.toException());
            }
        });
    }

    private void initRecyclerViewData() {
            dashboardList = new ArrayList<>();
        if (brand != null){
            if (brand.getCommissions().getCommissionGen() != null)
                commission = brand.getCommissions().getCommissionGen();
            Dashboard dashboard1 = new Dashboard("https://res.cloudinary.com/tellmedia/image/upload/v1581430268/shopmyinfluens/Brands/classic_brands/allbeauty1.png", "236€", "Chiffres d'affaire");
            Dashboard dashboard2 = new Dashboard("https://res.cloudinary.com/tellmedia/image/upload/v1581430268/shopmyinfluens/Brands/classic_brands/allbeauty1.png", commission + "€", "Commissions");
            Dashboard dashboard3 = new Dashboard("https://res.cloudinary.com/tellmedia/image/upload/v1581430268/shopmyinfluens/Brands/classic_brands/allbeauty1.png", "53", "Nombre de ventes");

            dashboardList.add(dashboard1);
            dashboardList.add(dashboard2);
            dashboardList.add(dashboard3);
        } else if (newBrand != null){
            Dashboard dashboard1 = new Dashboard("https://res.cloudinary.com/tellmedia/image/upload/v1581430268/shopmyinfluens/Brands/classic_brands/allbeauty1.png", "236€", "Chiffres d'affaire");
            Dashboard dashboard2 = new Dashboard("https://res.cloudinary.com/tellmedia/image/upload/v1581430268/shopmyinfluens/Brands/classic_brands/allbeauty1.png", "323€", "Commissions");
            Dashboard dashboard3 = new Dashboard("https://res.cloudinary.com/tellmedia/image/upload/v1581430268/shopmyinfluens/Brands/classic_brands/allbeauty1.png", "53", "Nombre de ventes");

            dashboardList.add(dashboard1);
            dashboardList.add(dashboard2);
            dashboardList.add(dashboard3);
        }

    }


    private void initRecyclerView() {
        initRecyclerViewData();
        dashboardRV.setLayoutManager(new LinearLayoutManager(mContext));
        dashboardRV.setHasFixedSize(true);
        dashboardAdapter = new DashboardAdapter(mContext, dashboardList);
        dashboardRV.setAdapter(dashboardAdapter);
    }


}