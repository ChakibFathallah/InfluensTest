package com.smi.test.views.home.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.smi.test.R;
import com.smi.test.models.Brand;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandVH> {

    private static final String TAG = BrandAdapter.class.getName();

    private Context context;
    private List<Brand> brandList;
    private OnBrandClickListener onBrandClickListener;


    public BrandAdapter(Context context, List<Brand> brandList, OnBrandClickListener onBrandClickListener) {
        this.context = context;
        this.brandList = brandList;
        this.onBrandClickListener = onBrandClickListener;
    }

    @NonNull
    @Override
    public BrandVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.brand_item, parent, false);

        return new BrandVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandVH holder, int position) {
        Brand currentBrand = brandList.get(position);
        try {
            Glide.with(context).load(currentBrand.getPic()).into(holder.img);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "onBindViewHolder: image => load failed " + e.getMessage());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBrandClickListener.onBrandClick(currentBrand);
            }
        });
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public class BrandVH extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        ShapeableImageView img;

        public BrandVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface OnBrandClickListener {
        void onBrandClick(Brand brand);
    }

    public void setOnBrandClickListener(OnBrandClickListener onBrandClickListener) {
        this.onBrandClickListener = onBrandClickListener;
    }
}
