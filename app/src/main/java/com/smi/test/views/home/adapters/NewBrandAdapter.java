package com.smi.test.views.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smi.test.R;
import com.smi.test.models.Dashboard;
import com.smi.test.models.NewBrand;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewBrandAdapter extends RecyclerView.Adapter<NewBrandAdapter.NewBrandVH> implements Filterable {

    private Context context;
    private List<NewBrand> newBrandList;
    private List<NewBrand> newBrandListFull;
    private OnNewBrandClickListener onNewBrandClickListener;

    public NewBrandAdapter(Context context, List<NewBrand> newBrandList, OnNewBrandClickListener onNewBrandClickListener) {
        this.context = context;
        this.newBrandList = newBrandList;
        this.newBrandListFull = new ArrayList<>(newBrandList);
        this.onNewBrandClickListener = onNewBrandClickListener;
    }


    @NonNull
    @Override
    public NewBrandVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_brand_item, parent, false);

        return new NewBrandVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewBrandVH holder, int position) {
        NewBrand currentNewBrand = newBrandList.get(position);

        try {
            Glide.with(context).load(currentNewBrand.getPic()).into(holder.img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.ammount.setText(currentNewBrand.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNewBrandClickListener.onNewBrandClick(currentNewBrand);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newBrandList.size();
    }

    @Override
    public Filter getFilter() {
        return newBrandsFiltered;
    }

    Filter newBrandsFiltered = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<NewBrand> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0)
                filteredList.addAll(newBrandListFull);
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (NewBrand newBrand : newBrandListFull) {
                    if (newBrand.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(newBrand);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            newBrandList.clear();
            newBrandList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class NewBrandVH extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        ImageView img;

        @BindView(R.id.ammount)
        TextView ammount;

        public NewBrandVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnNewBrandClickListener {
        void onNewBrandClick(NewBrand newBrand);

    }

    public void setOnNewBrandClickListener(OnNewBrandClickListener onNewBrandClickListener) {
        this.onNewBrandClickListener = onNewBrandClickListener;
    }


}
