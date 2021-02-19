package com.smi.test.views.home.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.smi.test.R;
import com.smi.test.models.Brand;
import com.smi.test.models.Dashboard;
import com.smi.test.models.Purchase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardVH> implements Filterable {

    private FragmentActivity context;
    private List<Dashboard> dashboardList;
    private List<Dashboard> dashboardListFull;
    private OnDashboardClickListener onDashboardClickListener;

    public DashboardAdapter(FragmentActivity context, List<Dashboard> dashboardList) {
        this.context = context;
        this.dashboardList = dashboardList;
        this.dashboardListFull = new ArrayList<>(dashboardList);
    }

    public DashboardAdapter(FragmentActivity context, List<Dashboard> dashboardList, OnDashboardClickListener onDashboardClickListener) {
        this.context = context;
        this.dashboardList = dashboardList;
        this.onDashboardClickListener = onDashboardClickListener;
    }

    @NonNull
    @Override
    public DashboardVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_item, parent, false);

        return new DashboardAdapter.DashboardVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardVH holder, int position) {
        Dashboard currentDashboard = dashboardList.get(position);

/*        if (currentDashboard.getCurrency().equals("EUR"))
             holder.ammount.setText(currentDashboard.getAmount() + "€");
         else
              holder.ammount.setText(currentDashboard.getAmount() + "$");*/

        //holder.img.setImageResource(currentDashboard.getImg());

        try {
            Glide.with(context).load(currentDashboard.getPic()).into(holder.img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.ammount.setText(currentDashboard.getAmmount());
        holder.title.setText(currentDashboard.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDashboardClickListener.onDashboardClick(currentDashboard);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dashboardList.size();
    }

    @Override
    public Filter getFilter() {
        return brandsFiltered;
    }

    Filter brandsFiltered = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Dashboard> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0)
                filteredList.addAll(dashboardListFull);
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Dashboard brand : dashboardListFull) {
                    if (brand.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(brand);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dashboardList.clear();
            dashboardList.addAll((List) results.values);
            notifyDataSetChanged();
        }

    };

    public class DashboardVH extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        ImageView img;

        @BindView(R.id.ammount)
        TextView ammount;

        @BindView(R.id.title)
        TextView title;

        public DashboardVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnDashboardClickListener {
        void onDashboardClick(Dashboard dashboard);
    }

    public void setOnDashboardClickListener(OnDashboardClickListener onDashboardClickListener) {
        this.onDashboardClickListener = onDashboardClickListener;
    }


}
