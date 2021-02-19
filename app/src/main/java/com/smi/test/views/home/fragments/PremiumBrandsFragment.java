package com.smi.test.views.home.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smi.test.R;
import com.smi.test.models.Brand;
import com.smi.test.views.home.HomeActivity;
import com.smi.test.views.home.adapters.BrandAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PremiumBrandsFragment extends Fragment implements BrandAdapter.OnBrandClickListener {

    private static final String TAG = PremiumBrandsFragment.class.getName();
    private static final String BrandListPremiumArgs = "BrandListPremiumArgs";


    @BindView(R.id.premium_brands_rv)
    RecyclerView premiumBrandRV;

    private FragmentActivity mContext;
    private BrandAdapter brandAdapter;
    private List<Brand> premiumBrandList;

    public PremiumBrandsFragment() {
        // Required empty public constructor
    }

    public static PremiumBrandsFragment newInstance(ArrayList<Brand> brandList) {
        PremiumBrandsFragment fragment = new PremiumBrandsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(BrandListPremiumArgs, brandList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        if (getArguments() != null) {
            premiumBrandList = new ArrayList<>(getArguments().getParcelableArrayList(BrandListPremiumArgs));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_premium_brands, container, false);
        ButterKnife.bind(this, view);

        initRecyclerView();

        return view;
    }


    private void initRecyclerView() {
        premiumBrandList = new ArrayList<>(premiumBrandList);
        premiumBrandRV.setLayoutManager(new GridLayoutManager(mContext, 3));
        brandAdapter = new BrandAdapter(mContext, premiumBrandList, this::onBrandClick);
        premiumBrandRV.setAdapter(brandAdapter);
    }

    @Override
    public void onBrandClick(Brand brand) {
        ((HomeActivity) mContext).switchFragment(DetailBrandFragment.newInstance(brand), DetailBrandFragment.class.getName());
    }
}